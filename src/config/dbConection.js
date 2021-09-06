const mariadb = require("mariadb");

const { DB_HOST, DB_USER, DB_PWD, DATABASE } = process.env;

async function query(query) {
  try {
    const pool = await mariadb.createPool({
      host: DB_HOST,
      user: DB_USER,
      password: DB_PWD,
      connectionLimit: 5,
      database: DATABASE,
    });

    db = await pool.getConnection();

    const rows = await db.query(query);

    return rows;
  } catch (error) {
    console.log("Error db", error);
  }
}

async function tryConnection() {
  const pool = await mariadb.createPool({
    host: DB_HOST,
    user: DB_USER,
    password: DB_PWD,
    connectionLimit: 5,
    database: DATABASE,
  });

  try {
    console.log("🦭  Trying to connect to MariaDB 🦭");
    await pool.getConnection();
    pool.end();
    console.log("🦭  MariaDB connection ready 🦭");
  } catch (err) {
    console.log("💥  Could not connect to MariaDB 💥");
  }
}

module.exports = {
  query,
  tryConnection,
};
