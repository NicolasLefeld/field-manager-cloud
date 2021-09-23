const { query } = require("../../config/dbConection");
const { ACTIVATED_ACTIVADO } = require("../../config/constants.json");

async function getUsuario(email) {
  try {
    return await query(
      `SELECT * FROM Usuarios WHERE email = '${email}'`
    );
  } catch (error) {
    console.log("Error db", error);
  }
}

module.exports = {
  getUsuario
};
