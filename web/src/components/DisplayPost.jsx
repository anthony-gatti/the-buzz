import DisplayMessage from "./DisplayMessage";
import React, { useState, useEffect } from 'react';
import "../styles/DisplayPost.css"

/** 
 * Displays a list of DisplayMessage components using data retrieved from backend/database
*/
const DisplayPost = (props) => {
  /**
   * Hook to hold the messages recieved from the backend/database
   */
  const [getMessages, setGetMessages] = useState([]);

    useEffect(() => {
        // start of fetch data
        const fetchData = async (key) => {
            try {
                // Make a GET request when the component mounts
                const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages`, {
                  method: 'GET',
                  headers: {
                      'Content-type': 'application/json; charset=UTF-8',
                      'Session-Key': key,
                  }
                });

                if (response.ok) {
                      // Put the response data into data and then set the messages to the data received from the server
                    const data = await response.json();
                    setGetMessages(data.mData);
                    console.log("start data");
                    console.log(data.mData);
                    console.log("end data");
                }else{ 
                  // throw error if the server response is not okay
                    throw new Error(`The server replied not ok: ${response.status}\n${response.statusText}`);
                }

            } catch (error) {
              // error if the get rquest was rejected/didn't go through
                console.warn('Error fetching messages:', error);
                //window.alert('Unspecified error');
            }
        }; // end of fetch data
        let key = import.meta.env.VITE_SESSION_KEY;
        if(props.getToken() != ""){
          key = props.getToken();
        }
        fetchData(key);
    }, [props.getToken]); // Runs whenever there is a login or out
    
    /**
     * Needs to be used to allow for functionality of comments page
     * @returns 1
     */
    const fakeGetVotes = () => {
      return 1;
    }

    
    /**
     * Returns the actual html code for this component.
     * A header as well as an unordered list of DisplayMessage
     * Note: the key prop is not used in the componenent, but needs to stay so there is alwyas a differentiating aspect
     */
    return (
      <>
      <h2 className="recentPosts">Recent Posts</h2>
      <div className="messageSection">
          <ul>
              {getMessages.slice().reverse().map(message => (
                  <DisplayMessage key={message.mId} title={message.mSubject} username={message.uUsername} message = {message.mMessage} url = {message.mURL} file = {message.mFile} getVotes={fakeGetVotes}
                    mid={message.mid} uid={message.uid} votes={message.mUpvotes - message.mDownvotes} changePage={props.changePage} getToken={props.getToken} 
                    getUpvote={() => {return message.mIsUpvote}} getDownvote={() => {return message.mIsDownvote}}/>
              ))}
          </ul>
      </div>
      </>
    );
  }; 

  export default DisplayPost;