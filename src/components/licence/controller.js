const {
  getDevice,
  getClient,
  getLicence,
  getPeriods,
  getLastActivacion,
  persist
} = require("./request");
const {
  LICENCE_RESPONSE_UNKNOWN,
  LICENCE_RESPONSE_WRONG_HARDWARE,
  LICENCE_RESPONSE_EXPIRED,
  LICENCE_RESPONSE_INVALID_LICENCE,
  LICENCE_RESPONSE_INVALID_CLIENT,
  LICENCE_RESPONSE_ACTIVATED,
  ACTIVATED_ACTIVADO,
  ACTIVATED_ERROR
} = require("../../config/constants.json");
const { encodeData } = require("../../util/encodeData");
const { date } = require("joi");

async function activate(ip, { command, data }) {
  let status = 200,
    body = {
      respuesta: LICENCE_RESPONSE_UNKNOWN
    };
  if (command === "activate") {
    const dataParsed = JSON.parse(data);
    const today = new Date().toISOString();

    const [deviceDbInfo] = await getDevice(dataParsed.device_id);

    let activation = {
      cuando: today,
      actived: ACTIVATED_ERROR,
      ip,
      dispositivo: deviceDbInfo
    }

    const [client] = await getClient(dataParsed.client_number);

    if (client) {
      const [licence] = await getLicence(
        dataParsed.licence_number,
        client.id
      );

      if (licence) {
        activation.licencia = licence;

        const [periods] = await getPeriods(licence.id, today);

        if (periods) {
          periods.desde = periods.desde.toISOString().substr(0,10)
          periods.hasta = periods.hasta.toISOString().substr(0,10)
          
          const [lastActivation] = await getLastActivacion(licence.id);

          if (lastActivation && deviceDbInfo.hadwareId === dataParsed.device_id) {
            const encodedData = encodeData(dataParsed, client.privateKey, client, periods)

            body.respuesta = LICENCE_RESPONSE_ACTIVATED;
            body.userMessage = "TestLic";
            body.jsonLicCryp = [encodedData];

            activation.actived = ACTIVATED_ACTIVADO;
          } else {
            body.respuesta = LICENCE_RESPONSE_WRONG_HARDWARE;
          }
        } else {
          body.respuesta = LICENCE_RESPONSE_EXPIRED;
        }
      } else {
        body.respuesta = LICENCE_RESPONSE_INVALID_LICENCE;
      }
    } else {
      body.respuesta = LICENCE_RESPONSE_INVALID_CLIENT;
    }
  } else if (command === "report") {
    body.respuesta = "wrong seed";
  } else {
    body.respuesta = "wrong seed";
  }

  await persist(activation);

  return { status, body };
}

module.exports = {
  activate,
};
