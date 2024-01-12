import PersonalInfo from '../components/PersonalInfo';
import Demographics from '../components/Demographics';
import Bio from '../components/Bio';
import "../styles/ProfilePage.css";
import { useState, useEffect } from 'react';

// this component displays the profile page
function ProfilePage(props){
    /**
     * Hook to hold the info about a user
     */
    const [info, setInfo] = useState({});
    /**
     * Hook to see if the user is currently editting the name or not, to determine what will be shown to them
     */
    const [nameEdit, setNameEdit] = useState(false);
    /**
     * Hook to hold the user currently on the page so we can tell if they should be able to edit or not
     */
    const[user, setUser] = useState(info.uId);
    /**
     * Used to pass changing values as props
     * @returns info object about the user
     */
     const getInfo = () => {
        return info;
    }
    /**
     * Adjust value of name
     */
    const [name, setName] = useState(getInfo().name);
    useEffect(() => {
        setName(info.name)
        setUser(info.uId);
    }, [getInfo().uUsername])
    /**
     * Function to toggle which button is showing (edit or save) and update the value of note itself
     */
    const nameEditing = () => {
        /**
         * Gets the input field itself that the user has been putting the editted value into
         */
        const element = document.getElementById("name");
        /**
         * Updates the hook with whatever the user has typed in so it can be used easily in the future
         */
        setName(element.value);
        /**
         * Function to actually change the value of note in the database.
         * Includes the session key of the user in the headers
         */
        const changeName = async () => {
            const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/users/name/${user}`, {
              method: 'PUT', 
              body: JSON.stringify({
                uName: `${element.value}`,
              }),
              headers: {
                'Content-Type': 'application/json',
                'Session-Key': props.getToken(),
              },
            });
      
            if (response.ok) {
              console.log("Successfully changed name");
            } else {
              console.log(response);
              console.error('Failed to change name');
            }
        }
        /**
         * Calls the function we just defined
         */
        changeName();
        /**
         * Toggles the hook so the input field and save button will be replaced by the normal display and edit button
         */
        setNameEdit(false);
    }
    const getName = () => {
        return name;
    }
    /**
     * Runs once the page loads, retrieves the information about the user
     */
    useEffect(() => {
        // start of fetch data
        const fetchData = async () => {
            try {
                // Make a GET request when the component mounts
                const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/users/${props.uid}`, {
                  method: 'GET',
                  headers: {
                      'Content-type': 'application/json; charset=UTF-8',
                      'Session-Key': import.meta.env.VITE_SESSION_KEY,
                      "Request-Cache": "max-age=3600",
                  }
                });

                if (response.ok) {
                     // Put the response data into data and then set the messages to the data received from the server
                    const data = await response.json();
                    setInfo(data.mData);
                    console.log(data.mData);
                }else{ 
                  // throw error if the server response is not okay
                   throw new Error(`The server replied not ok: ${response.status}\n${response.statusText}`);
                }

            } catch (error) {
              // error if the get rquest was rejected/didn't go through
                console.warn('Error fetching messages:', error);
                window.alert('Unspecified error');
            }
        }; // end of fetch data

        fetchData();
    }, []); // The empty dependency array ensures this effect runs once after the initial render

    /**
     * Actual html code for this component
     * A header with the user's name followed by the three child components, only showing the demographics if it is the same user
     */
    return (
        <>
        <div id="profileInfo">
            <h2>Profile</h2>
            {!nameEdit &&
                <>
                <h4>User: {getInfo().uName}</h4>
                {user == props.getUser() &&
                    <button data-testid="name-edit-button" className="edit" id="nameEdit" onClick={() => setNameEdit(true)}>Edit</button>
                }
                </>
            }
            {nameEdit &&
                <>
                <div className="fieldrow">
                    <label htmlFor="note">Note: </label>
                    <input data-testid="name-input" id="name" name="name" defaultValue={name}/>
                    <button data-testid="name-save-button" className="save" id="nameSave" onClick={nameEditing}>Save</button>
                </div>
                </>
            }
            
            <PersonalInfo getInfo={getInfo} getUser={props.getUser} getToken={props.getToken}/>
            {props.getUser() == props.uid &&
                <Demographics getInfo={getInfo} getToken={props.getToken}/>
            }
            <Bio getInfo={getInfo} getUser={props.getUser} getToken={props.getToken}/>
        </div>
        </>
    );
    }

    export default ProfilePage;