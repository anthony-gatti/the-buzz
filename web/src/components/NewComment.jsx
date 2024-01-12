import React, { useState} from 'react';
import "../styles/Comment.css";
/** 
 * The input section for adding a new comment
*/
function NewComment(props) {
    const [file, setFile] = useState('');
    const [filename, setFilename] = useState('');
    /**
     * Called when the user saves their new comment
     * Sends comment back to parent and backend
     */
    const addComment = () => {
      //Hide this component in display
      props.setAdd(false);
      //Get actual input element in this component
      const content = document.getElementById("content");
      const url = document.getElementById("url");
      //add comment and user to hook in parent containing new comments so we don't have to requery the backend
      props.addComment([...props.newComments, {
          content: content.value,
          url: url.value,
          file: file,
          filename: filename,
          user: props.getUser()
      }]);
      //Send Post request to backend to add the new comment to the database
      const addComment = async () => {
          try {
            const input = document.getElementById("content");
            const url = document.getElementById("url");
            const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/comments/${props.mid}`, {
              method: 'POST',
              // send message as json object
              body: JSON.stringify({
                cContent: input.value,
                cURL: url.value,
                cFile: file,
                fName: filename,
              }),
              headers: {
                'Content-type': 'application/json; charset=UTF-8',
                'Session-Key': props.getToken(),
              },
            });
            
            if (response.ok) {
              console.log("Comment added successfully"); 
              const data = await response.json();
              console.log(response);
            } else {
              // throw error if response is not okay
              throw new Error(`The server replied not ok: ${response.status}\n${response.statusText}`);
            }
          } catch (error) {
            // error if post request is rejected/ didnt go through
            console.warn('Something went wrong.', error);
            //window.alert('Unspecified error');
          }
        }; // end of addComment
        addComment();
    }
    /**
     * Sends message back to parent to hide this component
     */
    const cancelNewPost = () => {
      props.setAdd(false);
    }
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
     * Returns actual html for this component
     * Label, input area, save and cancel buttons
     */
    return (
        <>
        <div className="newCommentDiv">
            <div className="comment">
                <div>
                    <label htmlFor="content">Comment:</label>
                    <textarea data-testid="new-comment-input" id="content" name="content" rows="4" cols="50"></textarea>
                </div>
                <div>
                    <label htmlFor="url">Link:</label>
                    <input data-testid="new-comment-input" id="url" name="url" rows="1"></input>
                </div>
                <div>
                    <label htmlFor="file">Upload a file:</label>
                    <input data-testid="new-comment-input" type="file" id="file" name="file" onChange={handleFileChange}></input>
                </div>
                <div>
                    <button className="save" id="contentSave" onClick={addComment}>Comment</button>
                    <button className="cancel" onClick={cancelNewPost}>Cancel</button>
                </div>
            </div>
        </div>
        </>
    )
}
  
  export default NewComment;