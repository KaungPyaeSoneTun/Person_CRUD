function firebaseInit() {
	var firebaseConfig = {
			  apiKey: "BEjbxxiO6bLN10LK5eytSV1UV54mesg-XDADvt4KVx9Q0vKWGnEFN1ksf4Vt9DUglClORXWZ197S2Is0lUBCvB0",
			  authDomain: "kaung-pyae-sone-tun-poc-test.firebaseapp.com",
			  databaseURL: "https://kaung-pyae-sone-tun-poc-test.firebaseio.com",
			  projectId: "kaung-pyae-sone-tun-poc-test",
			  storageBucket: "kaung-pyae-sone-tun-poc-test.appspot.com",
			  messagingSenderId: "295489449130",
			  appId: "1:295489449130:web:86cee131b6e65fa70bf4d3",
			};
	
	firebase.initializeApp(firebaseConfig);
	const messaging = firebase.messaging();
	  const tokenDivId = 'token_div';
	  const permissionDivId = 'permission_div';

	  // [START receive_message]
	  // Handle incoming messages. Called when:
	  // - a message is received while the app has focus
	  // - the user clicks on an app notification created by a service worker
	  //   `messaging.setBackgroundMessageHandler` handler.
	  messaging.onMessage((payload) => {
	    console.log('Message received. ', payload);
	    // [START_EXCLUDE]
	    // Update the UI to include the received message.
	    appendMessage(payload);
	    // [END_EXCLUDE]
	  });
	  // [END receive_message]

	  function resetUI() {
	    clearMessages();
	    showToken('loading...');
	    // [START get_token]
	    // Get registration token. Initially this makes a network call, once retrieved
	    // subsequent calls to getToken will return from cache.
	    messaging.getToken({vapidKey: 'BEjbxxiO6bLN10LK5eytSV1UV54mesg-XDADvt4KVx9Q0vKWGnEFN1ksf4Vt9DUglClORXWZ197S2Is0lUBCvB0'}).then((currentToken) => {
	      if (currentToken) {
	        sendTokenToServer(currentToken);
	        updateUIForPushEnabled(currentToken);
	      } else {
	        // Show permission request.
	        console.log('No registration token available. Request permission to generate one.');
	        // Show permission UI.
	        updateUIForPushPermissionRequired();
	        setTokenSentToServer(false);
	      }
	    }).catch((err) => {
	      console.log('An error occurred while retrieving token. ', err);
	      showToken('Error retrieving registration token. ', err);
	      setTokenSentToServer(false);
	    });
	    // [END get_token]
	  }


	  function showToken(currentToken) {
	    // Show token in console and UI.
	    const tokenElement = document.querySelector('#'+ tokenDivId);
	    tokenElement.textContent = currentToken;
	  }

	  // Send the registration token your application server, so that it can:
	  // - send messages back to this app
	  // - subscribe/unsubscribe the token from topics
	  function sendTokenToServer(currentToken) {
	    if (!isTokenSentToServer()) {
	      console.log('Sending token to server...');
	      // TODO(developer): Send the current token to your server.
	      setTokenSentToServer(true);
	    } else {
	      console.log('Token already sent to server so won\'t send it again ' +
	          'unless it changes');
	    }

	  }

	  function isTokenSentToServer() {
	    return window.localStorage.getItem('sentToServer') === '1';
	  }

	  function setTokenSentToServer(sent) {
	    window.localStorage.setItem('sentToServer', sent ? '1' : '0');
	  }

	  function showHideDiv(divId, show) {
	    const div = document.querySelector('#' + divId);
	    if (show) {
	      div.style = 'display: visible';
	    } else {
	      div.style = 'display: none';
	    }
	  }

	  function requestPermission() {
	    console.log('Requesting permission...');
	    // [START request_permission]
	    Notification.requestPermission().then((permission) => {
	      if (permission === 'granted') {
	        console.log('Notification permission granted.');
	        // TODO(developer): Retrieve a registration token for use with FCM.
	        // [START_EXCLUDE]
	        // In many cases once an app has been granted notification permission,
	        // it should update its UI reflecting this.
	        resetUI();
	        // [END_EXCLUDE]
	      } else {
	        console.log('Unable to get permission to notify.');
	      }
	    });
	    // [END request_permission]
	  }

	  function deleteToken() {
	    // Delete registraion token.
	    // [START delete_token]
	    messaging.getToken().then((currentToken) => {
	      messaging.deleteToken(currentToken).then(() => {
	        console.log('Token deleted.');
	        setTokenSentToServer(false);
	        // [START_EXCLUDE]
	        // Once token is deleted update UI.
	        resetUI();
	        // [END_EXCLUDE]
	      }).catch((err) => {
	        console.log('Unable to delete token. ', err);
	      });
	      // [END delete_token]
	    }).catch((err) => {
	      console.log('Error retrieving registration token. ', err);
	      showToken('Error retrieving registration token. ', err);
	    });

	  }

	  // Add a message to the messages element.
	  function appendMessage(payload) {
	    const messagesElement = document.querySelector('#messages');
	    const dataHeaderElement = document.createElement('h5');
	    const dataElement = document.createElement('pre');
	    dataElement.style = 'overflow-x:hidden;';
	    dataHeaderElement.textContent = 'Received message:';
	    dataElement.textContent = JSON.stringify(payload, null, 2);
	    messagesElement.appendChild(dataHeaderElement);
	    messagesElement.appendChild(dataElement);
	  }

	  // Clear the messages element of all children.
	  function clearMessages() {
	    const messagesElement = document.querySelector('#messages');
	    while (messagesElement.hasChildNodes()) {
	      messagesElement.removeChild(messagesElement.lastChild);
	    }
	  }

	  function updateUIForPushEnabled(currentToken) {
	    showHideDiv(tokenDivId, true);
	    showHideDiv(permissionDivId, false);
	    showToken(currentToken);
	  }

	  function updateUIForPushPermissionRequired() {
	    showHideDiv(tokenDivId, false);
	    showHideDiv(permissionDivId, true);
	  }

	  resetUI();
}
