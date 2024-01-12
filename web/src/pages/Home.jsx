import DisplayPost from '../components/DisplayPost';
import NewPost from '../components/NewPost';
import "../styles/Home.css";

/**
 * Home page that contains a new post area if the user is logged in and a list of the posts regardless
 */
function Home(props) {

  return (
    <>
    <div className="post-section">
      { props.getUser() != "" &&
        <div> <NewPost getToken={props.getToken} /> </div>
      }
      <DisplayPost changePage={props.changePage} getToken={props.getToken} />
    </div>
    </>
  )
  }

export default Home;
