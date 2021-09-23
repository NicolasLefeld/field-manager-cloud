const { initializeApp } = require("firebase/app");
const {
  getAuth,
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  signInWithPopup,
} = require("firebase/auth");
const {
  getUsuario,
} = require("./request");

const firebaseConfig = {
  apiKey: "AIzaSyA6LxMvq9LxSK98GcFOgGFmtBj_qxyjrNo",
  authDomain: "fieldmanagerapp.firebaseapp.com",
  projectId: "fieldmanagerapp",
  storageBucket: "fieldmanagerapp.appspot.com",
  messagingSenderId: "625593883192",
  appId: "1:625593883192:web:76f73570d26219aeffa4cd",
  measurementId: "G-64R9TRVEHC",
};

initializeApp(firebaseConfig);

const userAndPasswordCreate = async (email, password) => {
  const auth = await getAuth();
  const { userCredential } = await createUserWithEmailAndPassword(
    auth,
    email,
    password
  );

  return userCredential;
};

const userAndPasswordLogin = async (email, password) => {
  const auth = getAuth();
  const logedIn = await signInWithEmailAndPassword(auth, email, password);

  return {
    status: 200,
    body: logedIn,
  };
};

const googleLogin = async (email) => {
  const [result] = await getUsuario(email);

  if (result) return 200;
  return 401;
};

module.exports = {
  userAndPasswordCreate,
  userAndPasswordLogin,
  googleLogin,
};
