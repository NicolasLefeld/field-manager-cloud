const {
  SIGNATURE_LINE_LENGTH,
  LICENSE_BEGIN,
  LICENSE_END,
  SIGNATURE_BEGIN,
  SIGNATURE_END
} = require("../config/constants.json");
const { EOL } = require("os");

function encodeData(data, privateKey64, { nombre }, { desde, hasta }) {
  const dataParsed = {
    device_id: data.device_id,
    client_name: nombre,
    licence_number: data.licence_number,
    client_number: data.client_number,
    expiration_date: hasta,
    start_date: desde,
  };
  const dataStringified = JSON.stringify(dataParsed);
  const signatureString = privateKey64;
  const signatureStringLength = signatureString.length;
  let key;

  for (let i = 0; i < signatureStringLength; i = i + SIGNATURE_LINE_LENGTH) {
    key += signatureString.substr(
      i,
      Math.min(signatureStringLength - i, SIGNATURE_LINE_LENGTH)
    );

    if (signatureStringLength - i > SIGNATURE_LINE_LENGTH) {
      key += EOL;
    }
  }

  const result = `${LICENSE_BEGIN}${EOL}${dataStringified}${EOL}${LICENSE_END}${EOL}${SIGNATURE_BEGIN}${EOL}${key}${EOL}${SIGNATURE_END}`;
  return result;
}

module.exports = {
  encodeData,
};
