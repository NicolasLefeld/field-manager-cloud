const { initializeApp } = require("firebase/app");
const {
  getAuth,
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  signInWithPopup,
} = require("firebase/auth");

const firebaseConfig = {
  apiKey: "API_KEY",
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
