require("dotenv").config();
const express = require("express");
const cors = require("cors");

const app = express();

app.use(cors())
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use("/licence", require("./components/licence/router"));
app.use("/auth", require("./components/auth/router"));

module.exports = app;
