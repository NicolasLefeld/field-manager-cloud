const app = require("./src/app");

app.listen(process.env.PORT, async () => {
  console.log(
    "---------------------------------------------------------------------------------\n\n"
  );
  console.log(`🏁 Server on ${process.env.PORT} 🏁`);
});
