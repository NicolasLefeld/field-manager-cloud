const { query } = require("../../config/dbConection");
const { ACTIVATED_ACTIVADO } = require("../../config/constants.json");

async function getDevice(device_id) {
  try {
    return await query(
      `SELECT * FROM Dispositivo WHERE hadwareId = '${device_id}' ORDER BY creacion DESC`
    );
  } catch (error) {
    console.log("Error db", error);
  }
}

async function getClient(client_id) {
  try {
    return await query(
      `SELECT * 
      FROM Cliente 
      WHERE nroCliente = '${client_id}'`
    );
  } catch (error) {
    console.log("Error db", error);
  }
}

async function getLicence(licence_id, client_id) {
  try {
    return await query(
      `SELECT * 
      FROM Licencia 
      WHERE nroLicencia = '${licence_id}' AND cliente_id = '${client_id}'`
    );
  } catch (error) {
    console.log("Error db", error);
  }
}

async function getPeriods(licence_id, today) {
  try {
    return await query(
      `SELECT * 
      FROM Periodo 
      WHERE licencia_id = '${licence_id}' AND desde <= '${today}' AND hasta >= '${today}'`
    );
  } catch (error) {
    console.log("Error db", error);
  }
}

async function getLastActivacion(licence_id) {
  try {
    return await query(
      `SELECT * 
      FROM Activacion 
      WHERE licencia_id = '${licence_id}' AND actived = '${ACTIVATED_ACTIVADO}'
      ORDER BY cuando DESC`
    );
  } catch (error) {
    console.log("Error db", error);
  }
}

module.exports = {
  getDevice,
  getClient,
  getLicence,
  getPeriods,
  getLastActivacion,
};
