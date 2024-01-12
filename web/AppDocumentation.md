# App Documentation

## Components

### New Post

NewPost is a component that allows the user to create a new post to the website. The components consists of various methods, such as clearForm, submitForm, doAjax, onSubmitPost. Using all of these methods, New Post sends a POST request to interact and send data to the backend, checks for valid input, and clears submission box. The method doAjax() specifically performs the POST request to the specified endpoint, by sending the title and message from the user as json objects, and if the response is okay, it puts the data recieved from the server into a variable(data) and passes that data into onSubmitPost. There is also error checking to communicate to the user and developer if something goes wrong within the request (server or network error, etc). OnSubmitPost(), which is called within doAjax(), is method that checks if the users post was succesfully created. It does this by seeing if the data response was okay, and if it was it clears the submission box using the clearForm method. There is also error checking within this function to show if there was an error within the submission to both the user and developer. The clearForm() method which is within the onSubmitPost, just sets both the title and message to empty strings. The NewPost component returns two input boxes for the title and message to post, and two buttons, which are "CREATE POST" and "Clear". The "CREATE POST" button calls the submitForm() method which validates the users input by checking something was entered in both fields and that the input was less than 2050 characters. The "Clear" button, calls the clearForm method to set both title and message to empty strings.

### Display Message

DisplayMessage is a component that passes in a variable props and returns the html layout of how a singular message is displayed. It returns the title for props (props.title) as a header and the message for props (props.message) as text, with an accompanying like button and delete button. The functionality of the of the like button is returned by returning the LikePost component with id passed in (props.id). The functionality of the of the delete post button is returned by returning the DeletePost component with id passed in (props.id).

### Display Post

DisplayPost is a component that displays all the posts on arrival of the website. The component uses the method fetchData() to send a get request to the specified endpoint. It checks if the status of the rquest was okay, and if it was it stores the data recieved into a variable data and sets the state variable setGetMessages to data.mData, so that the variable will be updated with the all the messages. There is also error checking within this function to show if there was an error within the submission to both the user and developer. The method fetchData is defined and called inside useEffect, which is a hook in React, and it delays the code within from running until the render is on the screen. The useEffect uses an empty array as an argument (shown before closing parenthesis), which tells the hook to only fetch data when the component mounts instead of also calling it when the component updates. The DisplayPost component returns a mapping of the getMessages variable, which has all the messages saved within it. It maps out/displays the messages out as a list using the format defined within the DisplayMessage component by calling it within the mapping passing in the individual titles, messages and ids.

### Like Post

LikePost is a component that allows the user to like a certain post. LikePost passes in the variable prop to be used within handle like to retrieve the id of a certain post to access the likes. Inside the method handleLike(), two put requests are sent depending if the function is liked or not. Using an if statement, the method checks if the post has already been liked. If it has been liked, a put request is sent to the specified endpoint for disliking, and if the response is okay, it passes false into the setLiked state variable to set the liked state variable to false, which removes the like. If the post is not liked (set to false), the method sends a different a put request to a different endpoint for liking and if the response is okay, it passes true into the setLiked state variable, which sets the state variable liked to true. Both put requests does error checking, to show if there was an error within the submission to both the user and developer. The LikePost component returns a button, that on click, performs the functionality of the handleLike() function. It uses the ternarary operator to determine what symbol will be displayed depending on what state the likes is in. If liked/true it displays "❤️ Liked" and if not liked/false it displays "♡ Like".

### Delete Post

DeletePost is a component that allows the user to delete a certain post.  DeletePost passes in the variable prop to be used within clickDelete() to retrieve the id of a certain post in order to remove it. Inside the method clickDelete() method, a delete request is used to remove a post. here is also error checking within this function to show if there was an error within the submission to both the user and developer. The component returns a button to delete a post thats calls on the functionality defined in clickDelete.

## Main Components

### App

App is a function that returns the formatting of the NewPost and DisplayPost components.

### Main

Main uses ReactDOM to render a the component App into a DOM element with the id 'root'.