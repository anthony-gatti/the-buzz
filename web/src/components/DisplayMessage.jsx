import LikePost from "./LikePost";
import "../styles/DisplayMessage.css";
import comments from "../assets/Comments.png";
import { useEffect } from "react";

/** 
 * A message/post, contains title, content, user who posted it, a button for comments, and a LikePost component
*/
function DisplayMessage(props){
    let fData, fName, mimeType;
    let mimeTypeParts, fileExtension;
    if(props.file){
        ({ fData, fName, mimeType } = props.file); 
        mimeTypeParts = mimeType.split('/');
        fileExtension = mimeTypeParts.length === 2 ? mimeTypeParts[1] : '';
    }
    /**
     * Change the page displayed to the user to the profile page of whichever user posted the message that was clicked on
     * Calls a hook passed as a prop from the parent component Home which comes from App.jsx to change the value of the hook
     */
    const routeProfile = () => {
        props.changePage(`profile/${props.uid}`);
    }
    /**
     * Change the page displayed to the user to the comment page of whichever message that was clicked on
     * Calls a hook passed as a prop from the parent component Home which comes from App.jsx to change the value of the hook
     */
    const routeComments = () => {
        props.changePage(`comments/${props.mid}/${props.username}`);
    }
    /**
     * Function whose sole purpose is to be passed as a prop since props.mid is not always defined in time as LikePost is made
     * @returns mid value, which user posted this message
     */
    const getMid = () => {
        return props.mid;
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
    
    /**
     * Returns the actual html code for this component.
     * Title, content, user who posted it, a button for comments, and a LikePost component
     */
    return (
        <>
        <div className="message">
            <h3 className="title">{props.title}</h3>
            <p className="content">{props.message}</p>
            <a className="url" href={props.url}>{props.url}</a>
            <p className="filename" onClick={handleFilenameClick}>{fName}</p>
            <p className="userLink" onClick={routeProfile}>{props.username}</p>
            <img src={comments} alt="comments" onClick={routeComments}></img>
            <span></span>
            <span className="likepost">
                < LikePost id= {props.mid} votes={props.votes} getVotes={props.getVotes}  getMid={getMid} getToken={props.getToken}
                    getUpvote={props.getUpvote} getDownvote={props.getDownvote} />
            </span>
            
        </div>
        </>
    );
    }

    export default DisplayMessage;