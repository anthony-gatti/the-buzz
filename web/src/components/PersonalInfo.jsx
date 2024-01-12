import { useState, useEffect } from "react";
/** 
 * The personal info section of the profile page, including a header and sections for the username and email, including editting capabilities
*/
function PersonalInfo(props){
    /**
     * Hook to see if the user is currently editting the username or not, to determine what will be shown to them
     */
    const [usernameedit, setUsernameedit] = useState(false);
    /**
     * Hook to see if the user is currently editting the email or not, to determine what will be shown to them
     */
    const [emailedit, setEmailedit] = useState(false);
    /**
     * Hook to hold the current value of the username section
     */
    const [username, setUsername] = useState(props.getInfo().uUsername);
    /**
     * Hook to hold the current value of the email section
     */
    const [email, setEmail] = useState(props.getInfo().uEmail);
    /**
     * Hook to hold the user currently on the page so we can tell if they should be able to edit or not
     */
    const[user, setUser] = useState(props.getInfo().uId);

    /**
     * Due to the async nature of the props, they may not be defined when the component is initially created.
     * Thus the useEffect, which runs everytime the note prop changes, will update the user, username, and email once they have values.
     * Similarly, the props are recieved through a function so they can change in the parent
     */
    useEffect(() => {
        setUsername(props.getInfo().uUsername);
        setEmail(props.getInfo().uEmail);
        setUser(props.getInfo().uId);
    }, [props.getInfo().uUsername])

    //Regex cheatsheet: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions/Cheatsheet
    // Start of string, have any amount of non-white space, an @ symbol, more nonwhitespace, a ., and some letters to finish it
    /**
     * Regex to ensure the user enters a valid email when they are editing
     * Start of string, have at least 1 non-white space character, an @, at least 1 non-whitespace character, a ., and at least 1 letter to finish
     */
    const emailRegex = /^(\S+)@(\S+)[.](\w+)$/;
    /**
     * Regex to ensure the user enters a valid username that is not just whitespace when they are editing
     * Have at least one non-whitespace character
     */
    const nonEmptyRegex = /(\S+)/;

    /**
     * Function to toggle which button is showing (edit or save) and update the value of username itself
     */
    const usernameEditing = () => {
        /**
         * Gets the input field itself that the user has been putting the editted value into
         */
        const element = document.getElementById("username");
        /**
         * Checks the validity of the username via regex comparison
         */
        if(nonEmptyRegex.test(element.value)){
            /**
             * Updates the hook with whatever the user has typed in so it can be used easily in the future
             */
            setUsername(element.value);
            /**
             * Function to actually change the value of note in the database.
             * Includes the session key of the user in the headers
             */
            const changeUsername = async () => {
                const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/users/username/${user}`, {
                  method: 'PUT', 
                  body: JSON.stringify({
                    uUsername: `${element.value}`,
                  }),
                  headers: {
                    'Content-Type': 'application/json',
                    'Session-Key': import.meta.env.VITE_SESSION_KEY,
                  },
                });
          
                if (response.ok && response.status != 200) {
                  console.log("Successfully changed username");
                  console.log(response);
                } else {
                  console.log(response);
                  console.error('Failed to change username');
                }
            }
            /**
             * Calls the function we just defined
             */
            changeUsername();
            /**
             * Toggles the hook so the input field and save button will be replaced by the normal display and edit button
             */
            setUsernameedit(false);
        }
        else{
            /**
             * Alert user if their input is not a valid username
             */
            //window.alert("Invalid username, enter a non-whitespace character");
        }
    }
    const emailEditing = () => {
        /**
         * Gets the input field itself that the user has been putting the editted value into
         */
        const element = document.getElementById("email");
        /**
         * Checks the validity of the email via regex comparison
         */
        if(emailRegex.test(element.value)){
            /**
             * Updates the hook with whatever the user has typed in so it can be used easily in the future
             */
            setEmail(element.value);
            /**
             * Function to actually change the value of email in the database.
             * Includes the session key of the user in the headers
             */
            const changeEmail = async () => {
                const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/users/email/${user}`, {
                  method: 'PUT', 
                  body: JSON.stringify({
                    uEmail: `${element.value}`,
                  }),
                  headers: {
                    'Content-Type': 'application/json',
                    'Session-Key': props.getToken(),
                  },
                });
          
                if (response.ok) {
                  console.log("Successfully changed email");
                } else {
                  console.log(response);
                  console.error('Failed to change email');
                }
            }
            /**
             * Calls the function we just defined
             */
            changeEmail();
            /**
             * Toggles the hook so the input field and save button will be replaced by the normal display and edit button
             */
            setEmailedit(false);
        }
        else{
            /**
             * Alert user if their input is not a valid email
             */
            //window.alert("Invalid email address");
        }
        
    }

    /**
     * Returns the actual html code for this component.
     * Will only display one of the two sections for each depending on if it is being editted or not.
     */
    return (
        <>
        <div id="personal">
            <h3>Personal Information</h3>
            <div className="valuePair">
                {!usernameedit &&
                    <>
                        <div className="fieldrow">
                            <p>Username: {username}</p>
                            {user == props.getUser() &&
                                <button data-testid="username-edit" className="edit" id="usernameEdit" onClick={() => setUsernameedit(true)}>Edit</button>
                            }
                        </div>
                    </>
                }
                {usernameedit && 
                    <>
                        <label htmlFor="username">Username: </label>
                        <input data-testid="username-input" id="username" name="username" defaultValue={username} type="text" />
                        <button data-testid="username-save" className="save" id="usernameSave" onClick={() => usernameEditing()}>Save</button>
                    </>
                }
            </div>
            <div className="valuePair">
                {!emailedit &&
                    <>
                        <div className="fieldrow">
                            <p>Email: {email}</p>
                            {user == props.getUser() &&
                                <button data-testid="email-edit" className="edit" id="emailEdit" onClick={() => setEmailedit(true)}>Edit</button>
                            }
                        </div>
                    </>
                }
                {emailedit && 
                    <>
                        <label htmlFor="email">Email: </label>
                        <input data-testid="email-input" id="email" name="email" defaultValue={email} type="text" />
                        <button data-testid="email-save" className="save" id="emailSave" onClick={() => emailEditing()}>Save</button>
                    </>
                }
            </div>
            <br />
        </div>
        </>
    );
    }

    export default PersonalInfo;