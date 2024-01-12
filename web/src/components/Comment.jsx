import "../styles/Comment.css";
import edit from "../assets/Edit.png";
import { useState } from "react";
/** 
 * A comment made on a post
 * Contains ability to edit if it is your own and the display of the creator
*/
function Comment(props) {
    /**
     * Hook to see if the user is currently editting the comment  or not, to determine what will be shown to them
     */
    const [contentEdit, setContentEdit] = useState(false);
    const [urlEdit, setURLEdit] = useState(false);
    const [fileEdit, setFileEdit] = useState(false);
    /**
     * Hook to hold the current value of the comment
     */
    const [content, setContent] = useState(props.message);
    const [url, setURL] = useState(props.url);
    const [file, setFile] = useState('');
    const [filename, setFilename] = useState('');
    let fData, fName, mimeType;
    let mimeTypeParts, fileExtension;
    if(props.file){
        ({ fData, fName, mimeType } = props.file); 
        mimeTypeParts = mimeType.split('/');
        fileExtension = mimeTypeParts.length === 2 ? mimeTypeParts[1] : '';
    }

    /**
     * Function to toggle which button is showing (edit or save) and update the value of the comment itself
     */
    const contentEditing = () => {
      /**
       * Gets the input field itself that the user has been putting the editted value into
       */
      const content = document.getElementById("content");
      const url = document.getElementById("url");
      /**
       * Function to actually change the value of comment in the database.
       * Includes the session key of the user in the headers
       */
      const changeContent = async () => {
        const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/comments/content/${props.cid}`, {
          method: 'PUT', 
          body: JSON.stringify({
            cContent: `${content.value}`,
          }),
          headers: {
            'Content-Type': 'application/json',
            'Session-Key': props.getToken(),
          },
        });
  
        if (response.ok) {
          console.log(response);
          console.log("Successfully changed comment");
        } else {
          console.error('Failed to change comment');
        }
      }
      const changeURL = async () => {
        const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/comments/url/${props.cid}`, {
          method: 'PUT', 
          body: JSON.stringify({
            cURL: `${url.value}`,
          }),
          headers: {
            'Content-Type': 'application/json',
            'Session-Key': props.getToken(),
          },
        });
  
        if (response.ok) {
          console.log("Successfully changed comment");
        } else {
          console.log(response);
          console.error('Failed to change comment');
        }
      }
      const changeFile = async () => {
        const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/comments/file/${props.cid}`, {
          method: 'PUT', 
          body: JSON.stringify({
            cFile: file,
            fName: filename,
          }),
          headers: {
            'Content-Type': 'application/json',
            'Session-Key': props.getToken(),
          },
        });
  
        if (response.ok) {
          console.log(response);
          console.log("Successfully changed comment");
        } else {
          console.log(response);
          console.error('Failed to change comment');
        }
      }
      if(contentEdit){
        /**
         * Updates the hook with whatever the user has typed in so it can be used easily in the future
         */
        setContent(content.value);
        /**
         * Calls the function we just defined
         */
        changeContent();
        /**
         * Toggles the hook so the input field and save button will be replaced by the normal display and edit button
         */
        setContentEdit(false);
      }else if(urlEdit){
        /**
         * Updates the hook with whatever the user has typed in so it can be used easily in the future
         */
        setURL(url.value);
        /**
         * Calls the function we just defined
         */
        changeURL();
        /**
         * Toggles the hook so the input field and save button will be replaced by the normal display and edit button
         */
        setURLEdit(false);
      }else if(fileEdit){
        /**
         * Calls the function we just defined
         */
        changeFile();
        /**
         * Toggles the hook so the input field and save button will be replaced by the normal display and edit button
         */
        setFileEdit(false);
      }
    }
    /**
     * Change the page displayed to the user to the profile page of whichever user made the comment that was clicked on
     * Calls a hook passed as a prop from the parent component CommentsPage which comes from App.jsx to change the value of the hook
     */
    const routeProfile = () => {
        props.changePage(`profile/${props.uid}`);
    }

    const handleFilenameClick = () => {
        // Decode the base64-encoded string to create a Blob
        const decodedData = atob(fData);

        // Convert the decoded data to a Uint8Array
        const uint8Array = new Uint8Array(decodedData.length);
        for (let i = 0; i < decodedData.length; ++i) {
            uint8Array[i] = decodedData.charCodeAt(i);
        }

        // Create a Blob from the Uint8Array
        const blob = new Blob([uint8Array], { type: 'application/octet-stream' });

        // Create a download link
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = fName;

        // Trigger a click on the link to initiate the download
        link.click();

        // Clean up
        URL.revokeObjectURL(link.href);
    };
    const handleFileChange = (e) => {
      const selectedFile = e.target.files[0];
  
      if (selectedFile) {
        setFilename(selectedFile.name);

        const reader = new FileReader();
  
        reader.onload = (event) => {
          const base64Data = btoa(event.target.result);
          setFile(base64Data);
        };
  
        reader.readAsBinaryString(selectedFile);
      }
    };

    /**
     * Returns the actual html code for this component.
     * Will only display one of the two sections depending on if it is being editted or not and will only let the user edit if they made it
     */
    return (
        <>
        <div className="comment">
            <div className="toprow">
                {!contentEdit &&  props.uid == props.getUser() && !url && !fName &&
                    <>
                      <p className="content">{content}</p>
                      <img data-testid="comment-edit-button" src={edit} alt="editContent" onClick={() => setContentEdit(true)}/>
                    </>
                }
                {!contentEdit &&  props.uid == props.getUser() && url && !fName &&
                    <>
                      <p className="content">{content}</p>
                      <img data-testid="comment-edit-button" src={edit} alt="editContent" onClick={() => setContentEdit(true)}/>
                      <a className="url" href={url}>{url}</a>
                      <img data-testid="comment-edit-button" src={edit} alt="editURL" onClick={() => setURLEdit(true)}/>
                    </>
                }
                {!contentEdit &&  props.uid == props.getUser() && !url && fName &&
                    <>
                      <p className="content">{content}</p>
                      <img data-testid="comment-edit-button" src={edit} alt="editContent" onClick={() => setContentEdit(true)}/>
                      <p className="filename" onClick={handleFilenameClick}>{fName}</p>
                      <img data-testid="comment-edit-button" src={edit} alt="editFile" onClick={() => setFileEdit(true)}/>
                    </>
                }
                {!contentEdit &&  props.uid == props.getUser() && url && fName &&
                    <>
                      <p className="content">{content}</p>
                      <img data-testid="comment-edit-button" src={edit} alt="editContent" onClick={() => setContentEdit(true)}/>
                      <a className="url" href={url}>{url}</a>
                      <img data-testid="comment-edit-button" src={edit} alt="editURL" onClick={() => setURLEdit(true)}/>
                      <p className="filename" onClick={handleFilenameClick}>{fName}</p>
                      <img data-testid="comment-edit-button" src={edit} alt="editFile" onClick={() => setFileEdit(true)}/>
                    </>
                }
                {!contentEdit && props.uid != props.getUser() &&
                    <>
                      <p className="content">{content}</p>
                      <a className="url" href={url}>{url}</a>
                      <p className="filename" onClick={handleFilenameClick}>{fName}</p>
                    </>
                }
                {contentEdit && 
                    <>
                        <label htmlFor="content">Comment: </label>
                        <textarea data-testid="comment-edit-input" id="content" name="content" rows="4" cols="50" defaultValue={content}></textarea>
                        <button data-testid="comment-save-button" className="save" id="contentSave" onClick={() => contentEditing()}>Save</button>
                    </>
                }
                {urlEdit && 
                    <>
                        <label htmlFor="url">Link: </label>
                        <input data-testid="new-comment-input" id="url" name="url" rows="1"></input>
                        <button data-testid="comment-save-button" className="save" id="contentSave" onClick={() => contentEditing()}>Save</button>
                    </>
                }
                {fileEdit && 
                    <>
                        <label htmlFor="file">Upload a file: </label>
                        <input data-testid="new-comment-input" type="file" id="file" name="file" onChange={handleFileChange}></input>
                        <button data-testid="comment-save-button" className="save" id="contentSave" onClick={() => contentEditing()}>Save</button>
                    </>
                }
            </div>
            <p className="userLink" onClick={routeProfile}>User {props.uid}</p>
        </div>
        </>
    )
    }
  
  export default Comment;