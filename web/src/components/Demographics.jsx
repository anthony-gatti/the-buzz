import { useState, useEffect } from "react";
/** 
 * The Demographics section of the profile page, including a header and sections for the gender and sexual orientation, and editting capabilities
*/
function Demographics(props){
    /**
     * Hook to see if the user is currently editting the sexual orientation or not, to determine what will be shown to them
     */
    const [soEdit, setSoEdit] = useState(false);
    /**
     * Hook to see if the user is currently editting the gender or not, to determine what will be shown to them
     */
    const [giEdit, setGiEdit] = useState(false);

    /**
     * Hook to hold the current value of the sexual orientation
     */
    const [so, setSo] = useState(props.getInfo().uSO);
    /**
     * Hook to hold the current value of the gender identity
     */
    const [gi, setGi] = useState(props.getInfo().mGender);
    /**
     * Hook to hold the user currently on the page so we can use it in the route
     * Note: We do not need to check the user for editting since this section is only displayed when the user can edit
     */
    const[user, setUser] = useState(props.getInfo().uId);

    /**
     * Due to the async nature of the props, they may not be defined when the component is initially created.
     * Thus the useEffect, which runs everytime the note prop changes, will update the user, gender, and sexual orientation once they have values.
     * Similarly, the props are recieved through a function so they can change in the parent
     */
    useEffect(() => {
        setSo(props.getInfo().uSO);
        setGi(props.getInfo().uGender);
        setUser(props.getInfo().uId);
    }, [props.getInfo().uGender])


    /**
     * Function to toggle which button is showing (edit or save) and update the value of sexual orientation itself
     */
    const soEditing = () => {
      /**
       * Gets the input field itself that the user has been putting the editted value into
       */
      const element = document.getElementById("so");
      /**
       * Updates the hook with whatever the user has typed in so it can be used easily in the future
       */
      setSo(element.value);
      /**
       * Function to actually change the value of sexual orientation in the database.
       * Includes the session key of the user in the headers
       */
      const changeSO = async () => {
          const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/users/so/${user}`, {
            method: 'PUT', 
            body: JSON.stringify({
              uSO: `${element.value}`,
            }),
            headers: {
              'Content-Type': 'application/json',
              'Session-Key': props.getToken(),
            },
          });
    
          if (response.ok) {
            console.log("Successfully changed so");
          } else {
            console.log(response);
            console.error('Failed to change so');
          }
        }
      /**
       * Calls the function we just defined
       */
      changeSO();
      /**
       * Toggles the hook so the input field and save button will be replaced by the normal display and edit button
       */
      setSoEdit(false);
    }
    /**
     * Function to toggle which button is showing (edit or save) and update the value of gender identity itself
     */
    const giEditing = () => {
      /**
       * Gets the input field itself that the user has been putting the editted value into
       */
      const element = document.getElementById("gi");
      /**
       * Updates the hook with whatever the user has typed in so it can be used easily in the future
       */
      setGi(element.value);
      /**
       * Function to actually change the value of gender in the database.
       * Includes the session key of the user in the headers
       */
      const changeGI = async () => {
          const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/users/gender/${user}`, {
            method: 'PUT', 
            body: JSON.stringify({
              uGender: `${element.value}`,
            }),
            headers: {
              'Content-Type': 'application/json',
              'Session-Key': props.getToken(),
            },
          });
    
          if (response.ok) {
            console.log("Successfully changed gi");
          } else {
            console.log(response);
            console.error('Failed to change gi');
          }
        }
      changeGI();
      /**
       * Toggles the hook so the input field and save button will be replaced by the normal display and edit button
       */
      setGiEdit(false);
    }

    /**
     * Returns the actual html code for this component.
     * Will only display one of the two sections for each depending on if it is being editted or not.
     */
    return (
        <>
         <div id="demographics">
                <h3>Demographics</h3>
                { soEdit &&
                    <>
                        <div className="valuePair">
                            <label htmlFor="so">Sexual Orientation: </label>
                            <select data-testid="soField" id="so" name="so">
                                <option value="none" disabled hidden>Select an Option</option> 
                                <option value="straight or heterosexual">Straight or heterosexual</option>
                                <option value="lesbian or gay">Lesbian or gay</option>
                                <option value="bisexual">Bisexual</option>
                                <option value="queer, pansexual, and/or questioning">Queer, pansexual, and/or questioning</option>
                                <option value="other">Other</option>
                                <option value="dont know">Don't know</option>
                                <option value="decline to answer">Decline to answer</option>
                            </select>
                        </div>
                        <button data-testid="so-save-button" className="save" id="soSave" onClick={soEditing}>Save</button>
                    </>
                }
                {
                    !soEdit && 
                    <>
                        <p>Sexual Orientation: {so}</p>
                        <button data-testid="so-edit-button" className="edit" id="soEdit" onClick={() => setSoEdit(true)}>Edit</button>
                    </>
                }
                
                { giEdit &&
                    <div className="valuePair">
                        <label htmlFor="gi">Gender Identity: </label>
                        <select data-testid="giField" id="gi" name="gi">
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                            <option value="transgender man">Transgender man</option>
                            <option value="transgender woman">Transgender woman</option>
                            <option value="genderqueer">Genderqueer/gender nonconforming</option>
                            <option value="other">Other</option>
                            <option value="decline to answer">Decline to answer</option>
                        </select>
                        <button data-testid="gi-save-button" className="save" id="giSave" onClick={giEditing}>Save</button>
                    </div>
                }
                {
                    !giEdit &&
                    <>
                        <p>Gender Identity: {gi}</p>
                        <button data-testid="gi-edit-button" className="edit" id="giEdit" onClick={() => setGiEdit(true)}>Edit</button>
                    </>
                }
                <br />
            </div>
        </>
    );
    }

    export default Demographics;