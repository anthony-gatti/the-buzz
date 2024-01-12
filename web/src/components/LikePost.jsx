import  { useState, useEffect } from 'react';
import "../styles/LikePost.css";
import unupvoted from "../assets/UnUpvoted.png";
import upvoted from "../assets/Upvoted.png";
import undownvoted from "../assets/UnDownvoted.png";
import downvoted from "../assets/Downvoted.png";

/** 
 * The upvoting/downvoting part of a message/post
*/
function LikePost(props) {
  /**
   * Hook to hold if the post is upvoted or not
   */
  let  [liked, setLiked] = useState(false);
  /**
   * Hook to hold if the post is downvoted or not
   */
  let  [disliked, setDisliked] = useState(false);
  /**
   * Hook to hold the upvotes-downvotes of the post
   */
  const [likeCount, setLikeCount] = useState(props.votes);

  /**
   * Necessary since votes won't get passed down immediately
   */
  useEffect(() => {
    if(props.votes == "O"){
      //console.log(props.getVotes());
      setLikeCount(props.getVotes()); 
    }
  }, [props.getVotes, props.getVotes()])
  /**
   * Run when the message we are looking at updates
   * Gets whether the message has been upvoted or downvoted by the current user and sets hooks accordingly
   */
  useEffect(() => {
    //Someone is logged in
    if(props.getToken() != ""){
      //Initialize them both to false so nothing weird get saved
      setLiked(false);
      setDisliked(false);
       //Check if the message was upvoted
      if(props.getUpvote() == 1){
        setLiked(true);
      }
      else if(props.getDownvote() == 1){
        setDisliked(true);
      }
      //setLikeCount(props.votes); //Starts as NaN on comments page for some reason, this fixes that
    }
    else{
      setLiked(false);
      setDisliked(false);
    }
   //runs whenever these change
  }, [props.getMid(), props.getToken(), props.getToken, props.getUpvote])
  /**
   * Called when uvpote button is clicked to do whatever behavior is necessary depending on current values
   */
  const handleUpvote = async () => {
    if(props.getToken() == ""){
      return;
    }
    try {
      if (liked) {
        // If the post is upvoted, send a delete request to remove the upvote
        const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages/upvote/${props.id}`, {
          method: 'DELETE', 
          headers: {
            'Content-Type': 'application/json',
            'Session-Key': props.getToken(),
          },
        });

        if (response.ok) {
          setLiked(false); // set liked to false, no like
          setLikeCount(likeCount - 1); //decrement likecount locally, don't need another api call
        } else {
          console.error('Failed to remove the like');
        }
      } else if(disliked) {
        // If the post is downvoted, send post request of upvote and delete request of downvote
        const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages/upvote/${props.id}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Session-Key': props.getToken(),
            "Request-Cache": "max-age=3600",
          },
          body: JSON.stringify({
           
          }),
        });
        const response2 = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages/downvote/${props.id}`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
            'Session-Key': props.getToken(),
          },
          body: JSON.stringify({
           
          }),
        });

        if (response.ok && response2.ok) {
          setLiked(true); // set like to true, upvoted
          setDisliked(false); // No longer downvoted
          setLikeCount(likeCount + 2); //increment likecount locally by 2 since we are remvoing a downvote and adding an upvote
        } else {
          console.error('Failed to like the post');
        }
      }
      else {
        // If the post is not upvoted or downvoted, send a post request to add the like
        const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages/upvote/${props.id}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Session-Key': props.getToken(),
            "Request-Cache": "max-age=3600",
          },
          body: JSON.stringify({
           
          }),
        });

        if (response.ok) {
          setLiked(true); // set like to true, upvoted
          setLikeCount(likeCount + 1); //increment upvote locally
          console.log(response);
        } else {
          console.error('Failed to like the post');
        }
      }
    } catch (error) {
      console.warn('Something went wrong.', error);
    }
  };
  /**
   * Called when downvote button is clicked to do whatever behavior is necessary depending on current values
   */
  const handleDownvote = async () => {
    if(props.getToken() == ""){
      return;
    }
    try {
      if (disliked) {
        // If the post is downvoted, send a delete request to remove the downvote
        const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages/downvote/${props.id}`, {
          method: 'DELETE', 
          headers: {
            'Content-Type': 'application/json',
            'Session-Key': props.getToken(),
          },
        });

        if (response.ok) {
          setDisliked(false); // set disliked to false, no downvote
          setLikeCount(likeCount + 1); //increment likecount locally, no need for an api call
        } else {
          console.error('Failed to remove the like');
        }
      } else if(liked) {
        // If the post upvoted, remove it and downvote it instead
        const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages/downvote/${props.id}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Session-Key': props.getToken(),
            "Request-Cache": "max-age=3600",
          },
          body: JSON.stringify({
           
          }),
        });
        const response2 = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages/upvote/${props.id}`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
            'Session-Key': props.getToken(),
          },
          body: JSON.stringify({
           
          }),
        });

        if (response.ok && response2.ok) {
          setDisliked(true); // set disliked to true, downvoted
          setLiked(false); //set liked to false, not upvoted anymore
          setLikeCount(likeCount - 2); //decrement likeCount locally by 2
        } else {
          console.error('Failed to like the post');
        }
      }
      else {
        // If the post is not downvoted, send a post request to add the downvote
        const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/v3.0/messages/downvote/${props.id}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Session-Key': props.getToken(),
            "Request-Cache": "max-age=3600",
          },
          body: JSON.stringify({
           
          }),
        });

        if (response.ok) {
          setDisliked(true); //set disliked to true, downvoted
          setLikeCount(likeCount - 1); //decrement likecount locally
        } else {
          console.error('Failed to like the post');
        }
      }
    } catch (error) {
      console.warn('Something went wrong.', error);
    }
  };


  /**
   * Returns the actual html code for this component.
   * The total amount of upvotes-downvotes as well as upvote and downvote buttons
   */
  return (
    <>
    <div className="likeSection">
      <p>{likeCount}</p>
      <div className="post" data-testid="like-post-component">
        {liked ? <img src={upvoted} alt="upvoted" onClick={handleUpvote}></img> : <img src={unupvoted} alt="unupvoted" onClick={handleUpvote}></img>}
        {disliked ? <img src={downvoted} alt="downvoted" onClick={handleDownvote}></img> : <img src={undownvoted} alt="undownvoted" onClick={handleDownvote}></img>}
      </div>
    </div>
    </>
  );
};

export default LikePost;
