const {
  SIGNATURE_LINE_LENGTH,
  LICENSE_BEGIN,
  LICENSE_END,
  SIGNATURE_BEGIN,
  SIGNATURE_END,
  PRIVATE_KEY_BEGIN,
  PRIVATE_KEY_END,
} = require("../config/constants.json");
const { createSign } = require("crypto");
const { EOL } = require("os");

function encodeData(data, privateKey64, { nombre }, { desde, hasta }) {
  let key = "";
  const dataParsed = {
    device_id: data.device_id,
    client_name: nombre,
    licence_number: data.licence_number,
    client_number: data.client_number,
    expiration_date: hasta,
    start_date: desde,
  };
  const dataStringified = JSON.stringify(dataParsed);
  const sign = createSign("RSA-SHA256");

  sign.update("dataStringified");
  sign.end();

  const signature = sign.sign(
    `${PRIVATE_KEY_BEGIN}${EOL}${privateKey64}${EOL}${PRIVATE_KEY_END}`
  );
  const signatureString = Buffer.from(signature)
    .toString("base64")
    .toString("ascii");
  const signatureStringLength = signatureString.length;

  for (let i = 0; i < signatureStringLength; i = i + SIGNATURE_LINE_LENGTH) {
    key += signatureString.substr(
      i,
      Math.min(signatureStringLength - i, SIGNATURE_LINE_LENGTH)
    );

    if (signatureStringLength - i > SIGNATURE_LINE_LENGTH) {
      key += EOL;
    }
  }

  return `${LICENSE_BEGIN}${EOL}${dataStringified}${EOL}${EOL}${LICENSE_END}${EOL}${SIGNATURE_BEGIN}${EOL}${key}${EOL}${SIGNATURE_END}`;
}

module.exports = {
  encodeData,
};
