import DisplayMessage from "../components/DisplayMessage";
import Comment from "../components/Comment";
import NewComment from "../components/NewComment";
import { useState, useEffect } from "react";
import "../styles/CommentsPage.css";
import Add from "../assets/Add.png";
/**
 * Comments Page, displays the message and its associated comments
 */
function CommentsPage(props) {
  /**
   * Hook to see if the user is currently adding a new comment or not
   */
  const [add, setAdd] = useState(false);
  /**
   * Hook to hold a list of comments the user has now added
   */
  const [newComments, setNewComments] = useState([]);
  /**
   * Hook to hold the comments retrieved from the backend
   */
  const [comments, setComments] = useState([]);
  /**
   * Hook to hold the main message the comments are made about
   */
  const [post, setPost] = useState({});
  
  const getTotalVotes = () => {
    return post.mUpvotes - post.mDownvotes;
  }

  /**
   * Runs once page loads, retrieves all the comments associated with a particular post
   */
  useEffect(() => {
    // start of fetch data
    const fetchComments = async () => {
        try {
            // Make a GET request when the component mounts
            const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages/comments/${props.mid}`, {
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
                setComments(data.mData);
            }else{ 
              // throw error if the server response is not okay
              console.log("Error here");
              console.log(response);
               throw new Error(`The server replied not ok: ${response.status}\n${response.statusText}`);
            }

        } catch (error) {
          // error if the get rquest was rejected/didn't go through
            console.warn('Error fetching messages:', error);
            //window.alert('Unspecified error');
        }
    }; // end of fetch data

    fetchComments();
}, []); // The empty dependency array ensures this effect runs once after the initial render
    
  /**
   * Runs once the page loads, retrieves the main message the comments are made about
   */
    useEffect(() => {
        // start of fetch data
        const fetchData = async () => {
            try {
                // Make a GET request when the component mounts
                const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages/${props.mid}`, {
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
                    setPost(data.mData);
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

        fetchData();
    }, [])

    

  /**
   * Returns actual html from the component
   * Main post, the comments retrieved from the backend, the new comments, and either a button to add new comments or the input itself
   */
  return (
    <>
      <div className="comments">
        <div className="commentMessage">
          <DisplayMessage title={post.mSubject} message={post.mMessage} url = {post.mURL} file = {post.mFile} uid={post.uid} mid={post.mid} username={props.uid}
            getVotes={getTotalVotes} votes="O" changePage={props.changePage} getToken={props.getToken}
            getUpvote={() => {return post.mIsUpvote}} getDownvote={() => {return post.mIsDownvote}}/>
        </div>
        {comments.map(comment => (
          <Comment key={comment.cId} cid={comment.cId} message={comment.cContent} url={comment.cURL} file={comment.cFile}
          uid={comment.uId} changePage={props.changePage} getUser={props.getUser} getToken={props.getToken}/>
        ))}

        {newComments.map(comment => (
            <Comment key={comment.cId} cid={comment.cId} message={comment.content} url={comment.cURL} file={comment.cFile}
            uid={comment.user} changePage={props.changePage} getUser={props.getUser} getToken={props.getToken}/>
        ))}
        
        {!add && props.getUser() != "" &&
          <div className="newCommentDiv"><img src={Add} alt="add comment" onClick={() => setAdd(true)} /></div>
        }
        {add &&
           <div className="newCommentDiv">
             <NewComment mid={post.mid} setAdd={setAdd} addComment={setNewComments} newComments={newComments} 
              getUser={props.getUser} getToken={props.getToken}/>
           </div>
        }
      </div>
    </>
  )
  }

export default CommentsPage;
