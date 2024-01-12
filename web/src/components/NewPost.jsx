import React, { useState} from 'react';
import "../styles/NewPost.css";

// this component sends a post request to the server, checks for valid input, and clears submission box on okay response
export const NewPost = (props) => {
    // need state variables so values will be updated with change
    const [title, setTitle] = useState('');
    const [message, setMessage] = useState('');
    const [url, setURL] = useState('');
    const [file, setFile] = useState('');
    const [filename, setFilename] = useState('');
  
    //clear form
    const clearForm = () => {
      setTitle('');
      setMessage('');
      setURL('');
      setFile('');
      setFilename('');
    };
  
    // submit form checks if the title and message field have input and that the input is less than 2050 characters
    // if the input is valid it performs the doAjax method
    const submitForm = () => {
      if (title === '' || message === '' || message.length > 2050) {
        console.log("valid input not provided")
        window.alert('Input not valid. Must include a title and message, with the message under 2050 characters');
        return;
      }
  
      doAjax(); // call doAjax, when the form is submitted to perform post request
    }; // end of submit form
    
  
    // doAjax performs a post request to the specified enpoint and calls onSubmit function if the status is okay
    const doAjax = async () => {
      try {
        const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages`, {
          method: 'POST',
          // send title and message as json objects
          body: JSON.stringify({
            mSubject: title,
            mMessage: message,
            mURL: url,
            mFile: file,
            fName: filename,
          }),
          headers: {
            'Content-type': 'application/json; charset=UTF-8',
            'Session-Key': props.getToken(),
            "Request-Cache": "max-age=3600",
          },
        });
        console.log(response);
        // put the response data in data and pass it into onSubmit to check status of post
        if (response.ok) {
          const data = await response.json();
          onSubmitPost(data);
          console.log(data); 
        } else {
          // throw error if response is not okay
          throw new Error(`The server replied not ok: ${response.status}\n${response.statusText}`);
        }
      } catch (error) {
        // error if post request is rejected/ didnt go through
        console.warn('Something went wrong.', error);
        //window.alert('Unspecified error');
      }
    }; // end of doAjax
    
  
  
    // on Submit checks if the post was created 
    const onSubmitPost = (data) => {
      // if the status within the response is okay, the post was succesfully created and the form can be cleared
      if (data.mStatus === 'ok') {
        clearForm();
      // if response was negative then alert the user that the post wasnt created
      } else if (data.mStatus === 'error') {
        //window.alert('The server replied with an error:\n' + data.mMessage);
      } else { 
        //window.alert('Unspecified error');
      }
    }; // end of onSubmitPost method

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
  
    return (
      // section for creating a new post
      <>
      <div className="newPostHeader">
        <h4>Create a new Post</h4>
      </div>
      <div className = "inputField"> 
        <div className="newPostInput">
          <input
            type="text"
            placeholder="Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </div>
        <div className="newPostInput">
          <input
            type="text"
            placeholder="Message"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
          />
        </div>
        <div className="newPostInput">
          <input
            type="text"
            placeholder="Link"
            value={url}
            onChange={(e) => setURL(e.target.value)}
          />
        </div>
        <div className="newPostInput">
          <input
            type="file"
            onChange={handleFileChange}
            style={{ color: 'white' }}
          />
        </div>
        <div className = "postButtons">
        <button onClick={submitForm}>CREATE POST</button>
        <button onClick={clearForm}>Clear</button>
        </div>
      </div>
      </>
    );
  }; // END OF NEW POST METHOD

  export default NewPost;