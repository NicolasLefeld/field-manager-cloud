const { Result } = require("express-validator");

function encodeData(data, privateKey64, { nombre }, { desde, hasta }) {
  // MIIBSgIBADCCASsGByqGSM44BAEwggEeAoGBALxwvVh6pmpQXIoiZPzgauZCHSmMTQ2MnhrikcFRS7SbjRrEAE721BtnA8dOBc+RmmAMbGyeV9YVgAbitDjkIZdB35tFufZh6CsZHoWCeaHV/5oSzbb1aE0DYx95Y82rEVRiCWAB04EmtJz9s9ShCrf5uxKZTuafof8YYigAE5MjAhUAiV56JT7C0yVUsmZ8dakcebP0JPUCgYA2mVbOLANDeDrx9kURFLBF8USBgesLRfXgXkSiViw/xZvu4GKOoCrDp5M5ay1p2bBK4PvEEjDlmjFC7XcoKjUb6FBK2Ou9uYUwmWDiBaEZOQRmcqtVwFcen3OoLv5bl7zeBPlBjBTHvi4kxraoDgqOjIFkCDkSPwkXS18XUowNyQQWAhRNWTakVHLGu1pV2yrg8oaLHHvWJw==
  const key = privateKey64;
  const dataParsed = {
    device_id: data.device_id,
    client_name: nombre,
    licence_number: data.licence_number,
    client_number: data.client_number,
    expiration_date: hasta,
    start_date: desde,
  };
  const dataStringified = JSON.stringify(dataParsed);

  const result = `----- BEGIN LICENSE -----\n${dataStringified}\n\n----- END LICENSE -----\n----- BEGIN SIGNATURE -----\n${key}\n----- END SIGNATURE -----`;
  return result;
}

module.exports = {
  encodeData,
};
