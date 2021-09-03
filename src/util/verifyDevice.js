function verifyDevice(currentDevice, storedDevice) {
  // This function it isnt used
  // The implementation should be something like this: (At /licence/controller.js)
  // const deviceVerified = verifyDevice(dataParsed, deviceDbInfo);

  if (!currentDevice.hadwareId === storedDevice.hadwareId) return false;
  
  if (
    currentDevice.users !== []
      ? !currentDevice.users === storedDevice.users
      : storedDevice.users !== ''
  )
    return false;
    
  if (
    currentDevice.fmversion !== ''
      ? !currentDevice.fmversion === storedDevice.fmversion
      : storedDevice.fmversion !== ''
  )
    return false;
    
  if (
    currentDevice.model !== ''
      ? !currentDevice.model === storedDevice.model
      : storedDevice.model !== ''
  )
    return false;
    
  if (
    currentDevice.extras !== ''
      ? !currentDevice.extras === storedDevice.extras
      : storedDevice.extras !== ''
  )
    return false;
    
  return currentDevice.language !== ''
    ? currentDevice.language === storedDevice.language
    : storedDevice.language == '';

}

module.exports = {
  verifyDevice,
};
