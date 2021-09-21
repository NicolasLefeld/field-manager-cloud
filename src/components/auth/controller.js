const { initializeApp } = require("firebase/app");
const {
  getAuth,
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  signInWithPopup,
} = require("firebase/auth");

const firebaseConfig = {
  apiKey: 'AIzaSyA6LxMvq9LxSK98GcFOgGFmtBj_qxyjrNo',
  authDomain: 'fieldmanagerapp.firebaseapp.com',
  projectId: 'fieldmanagerapp',
  storageBucket: 'fieldmanagerapp.appspot.com',
  messagingSenderId: '625593883192',
  appId: '1:625593883192:web:76f73570d26219aeffa4cd',
  measurementId: 'G-64R9TRVEHC',
};

initializeApp(firebaseConfig);

const userAndPasswordCreate = async (email, password) => {
  const auth = await getAuth();
  const { userCredential } = await createUserWithEmailAndPassword(
    auth,
    email,
    password
  );

  return {
    status: 200,
    body: userCredential,
  };
};

const userAndPasswordLogin = async (email, password) => {
  const auth = getAuth();
  const logedIn = await signInWithEmailAndPassword(auth, email, password);

  return {
    status: 200,
    body: logedIn,
  };
};

const googleLogin = async () => {
  try {
    const provider = new GoogleAuthProvider();

    const auth = getAuth();
    const result = await signInWithPopup(auth, provider);

    return {
      status: 200,
      body: result.user,
    };
  } catch (error) {
    return {
      status: error.code,
      body: error.message,
    };
  }
};

module.exports = {
  userAndPasswordCreate,
  userAndPasswordLogin,
  googleLogin,
};
