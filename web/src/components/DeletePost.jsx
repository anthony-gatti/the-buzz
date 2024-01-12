/**
 * NOT CURRENTLY IN USE
 */

const backendUrl = "https://team-7-11.dokku.cse.lehigh.edu";


function DeletePost(props) {
        const clickDelete = async () => {
                try {
                    const response = await fetch(`${backendUrl}/messages/${props.id}`, {
                        method: 'DELETE',
                        headers: {
                            'Content-type': 'application/json; charset=UTF-8',
                            'Session-Key': props.getToken(),
                        }
                    });

                    if (response.ok) {
                        const data = await response.json();
                        console.log(data);
                    } else {
                        window.alert(`The server replied not ok: ${response.status}\n${response.statusText}`);
                        throw new Error(errorText);
                    }
                } catch (error) {
                    console.warn('Something went wrong.', error);
                    //window.alert("Unspecified error");
                }
        };

    return (
        <button onClick={clickDelete}>
            Delete Post
        </button>
    );
};

export default DeletePost;