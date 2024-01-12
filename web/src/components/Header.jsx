import "../styles/Header.css";
import profile from '../assets/profile.png';
import { useEffect, useState } from 'react';
import { jwtDecode } from "jwt-decode";

/** 
 * The header shown at the top of all screens 
 * Contains a sign-in/out button and a title "The Buzz" linked to the homepage
 * When logged in there is a profile picture linking to the user's profile
*/
function Header(props){
    //Oauth code from this video: https://www.youtube.com/watch?v=roxC8SMs7HU

    /**
     * Used in the callback for when the User logs in with google
     * Sets user hook with what we get from Google/the backend
     * @param {*} response 
     */
    function handleCallbackResponse(response){
        const postUser = async (credential) => {
            try {
                // Make a GET request when the component mounts
                const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/users`, {
                  method: 'POST',
                  headers: {
                      'Content-type': 'application/json; charset=UTF-8',
                      'Session-Key': import.meta.env.VITE_SESSION_KEY,
                      'Id-Token-String': credential,
                      "Request-Cache": "max-age=3600",
                  }
                });

                if (response.ok) {
                    console.log("Response ok");
                      // Put the response data into data and then set the messages to the data received from the server
                    const data = await response.json();
                    console.log("data:" + data);
                    props.setCurrentUser(data.uid);
                    props.setToken(data.mSessionToken);
                }else{ 
                  console.log("Bad response");
                  // throw error if the server response is not okay
                    throw new Error(`The server replied not ok: ${response.status}\n${response.statusText}`);
                }

            } catch (error) {
              // error if the get rquest was rejected/didn't go through
                console.warn('Error fetching messages:', error);
                //window.alert('Unspecified error');
            }
        }; // end of Post user
        postUser(response.credential);
        localStorage.setItem('sessionCredential', JSON.stringify(response.credential));
        document.getElementById("signInDiv").hidden = true;
    }
    function alreadyLoggedIn(credential){
      const postUser = async (credential) => {
        try {
            // Make a GET request when the component mounts
            const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/users`, {
              method: 'POST',
              headers: {
                  'Content-type': 'application/json; charset=UTF-8',
                  'Session-Key': import.meta.env.VITE_SESSION_KEY,
                  'Id-Token-String': credential,
                  "Request-Cache": "max-age=3600",
              }
            });

            if (response.ok) {
                  // Put the response data into data and then set the messages to the data received from the server
                const data = await response.json();
                if(data.mStatus != ("error")){
                  props.setCurrentUser(data.uid);
                  props.setToken(data.mSessionToken);
                  document.getElementById("signInDiv").hidden = true;
                }                
            }else{ 
              // throw error if the server response is not okay
                console.log("Session expired");
            }

        } catch (error) {
          // error if the get rquest was rejected/didn't go through
            console.warn('Error fetching messages:', error);
        }
    }; // end of Post user
    postUser(credential);    
    }

    /**
     * Handles signout of google
     * Sets current user to blank and shows sign-in button
     * @param {*} event 
     */
    function handleSignout(event){
        props.setCurrentUser("");
        props.setToken("");
        localStorage.setItem('sessionCredential', "");
        document.getElementById("signInDiv").hidden = false;
    }
    /**
     * Run once when the page loads, sets up the google login process/button
     */
    useEffect(() => {
        /* global google */
        google.accounts.id.initialize({
          client_id: import.meta.env.VITE_CLIENT_ID,
          callback: handleCallbackResponse
        });
        google.accounts.id.renderButton(
          document.getElementById("signInDiv"),
          {
            theme: "outline", size: "medium"
          }
        )
        //Check to see if there is already a session credential in storage
        if(localStorage.getItem('sessionCredential') != null){
          alreadyLoggedIn(localStorage.getItem('sessionCredential').slice(1, localStorage.getItem('sessionCredential').length-1));
          //slice cuts off the preceding and terminating ""
        }
      }, [])
    /**
     * Change the page displayed to the user to the profile page of the current user
     * Calls a hook passed as a prop from the parent App.jsx to change the value of the hook
     */
    const routeProfile = () => {
        props.changePage(`profile/${props.getCurrentUser()}`);
    }
    /**
     * Change the page displayed to the user page with all the messages
     * Calls a hook passed as a prop from the parent App.jsx to change the value of the hook
     */
    const routeHome = () => {
        props.changePage("Home");
    }
    /**
     * Returns the actual html code for this component.
     * Sign-in/out button and header "The Buzz"
     */
    return (
        <>
        <div id="header">
            <div id="login">
                <div id="signInDiv"></div>
                { props.getCurrentUser() != "" &&
                    <>
                    <div className="vertFlex">
                        <img onClick={routeProfile} src={profile} alt="profile"/>
                        <button onClick={(e) => handleSignout(e)}>Sign Out</button>
                    </div>
                    </>
                }
            </div>
            <h1 id="buzz" onClick={routeHome}>The Buzz</h1>
            <div>    
            </div>
        </div>
        </>
    );
    }

    export default Header;