import { useEffect, useState } from "react";
/** 
 * The Bio section of the profile page, including a header and a section for the note, including editting capabilities
*/

function Bio(props){
    /**
     * Hook to see if the user is currently editting the note or not, to determine what will be shown to them
     */
    const [noteEdit, setNoteEdit] = useState(false);
    /**
     * Hook to hold the current value of the note section
     */
    const [note, setNote] = useState(props.gi);
    /**
     * Hook to hold the user currently on the page so we can tell if they should be able to edit or not
     */
    const[user, setUser] = useState(props.getInfo().uId);
    /**
     * Due to the async nature of the props, they may not be defined when the component is initially created.
     * Thus the useEffect, which runs everytime the note prop changes, will update the user and note once they have values.
     * Similarly, the props are recieved through a function so they can change in the parent
     */
    useEffect(() => {
        setNote(props.getInfo().uNote);
        setUser(props.getInfo().uId);
    }, [props.getInfo().uNote])

    /**
     * Function to toggle which button is showing (edit or save) and update the value of note itself
     */
    const noteEditing = () => {
        /**
         * Gets the input field itself that the user has been putting the editted value into
         */
        const element = document.getElementById("note");
        /**
         * Updates the hook with whatever the user has typed in so it can be used easily in the future
         */
        setNote(element.value);
        /**
         * Function to actually change the value of note in the database.
         * Includes the session key of the user in the headers
         */
        const changeNote = async () => {
            const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/users/note/${user}`, {
              method: 'PUT', 
              body: JSON.stringify({
                uNote: `${element.value}`,
              }),
              headers: {
                'Content-Type': 'application/json',
                'Session-Key': props.getToken(),
              },
            });
      
            if (response.ok) {
              console.log("Successfully changed note");
            } else {
              console.log(response);
              console.error('Failed to change note');
            }
        }
        /**
         * Calls the function we just defined
         */
        changeNote();
        /**
         * Toggles the hook so the input field and save button will be replaced by the normal display and edit button
         */
        setNoteEdit(false);
    }

    /**
     * Returns the actual html code for this component.
     * Will only display one of the two sections depending on if it is being editted or not.
     */
    return (
        <>
        <div id="bio">
            <h3>Bio</h3>
            { noteEdit &&
                <>
                    <div className="fieldrow">
                        <label htmlFor="note">Note: </label>
                        <textarea data-testid="note-input" id="note" name="note" rows="5" cols = "50">{note}</textarea>
                        <button data-testid="note-save-button" className="save" id="noteSave" onClick={noteEditing}>Save</button>
                    </div>
                </>
            }
            { !noteEdit &&
                <>
                    <p>Note: {note}</p>
                    {user == props.getUser() &&
                        <button data-testid="note-edit-button" className="edit" id="noteEdit" onClick={() => setNoteEdit(true)}>Edit</button>
                    }
                </>
            }
            <br />
        </div>
        </>
    );
}

    export default Bio;