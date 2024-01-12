import CommentsPage from './pages/CommentsPage.jsx';
import Home from './pages/Home.jsx';
import ProfilePage from "./pages/ProfilePage.jsx";
import Header from './components/Header.jsx';
import { useState } from "react";
/**
 * Main app, holds all information about the app
 */
function App() {
  /**
   * Hook to hold which page the user is currently on
   */
  const [page, setPage] = useState("Home");
  /**
   * Hook to hold the current user, "" if not logged in
   */
  const [currentUser, setCurrentUser] = useState("");
  /**
   * Hook to hold the current session token, "" if not logged in
   */
  const [currentSessionToken, setCurrentSessionToken] = useState("");
  /**
   * Used to send changing information as props
   * @returns the current user
   */
  const getCurrentUser = () => {
    return currentUser;
  }
  /**
   * Used to send changing information as props
   * @returns the current sesion token
   */
  const getCurrentSessionToken = () => {
    return currentSessionToken;
  }

  /**
   * Returns actual html code for the app
   * Only returns the header and one page, depending on which page the user should be routed to 
   */
  return (
    <>
    <Header changePage={setPage} setCurrentUser={setCurrentUser} setToken={setCurrentSessionToken} getCurrentUser={getCurrentUser}/>
    {page=="Home" &&
      <Home changePage={setPage} getUser={getCurrentUser} getToken={getCurrentSessionToken} />
    }
    { page.split("/")[0]=="profile" &&
      <ProfilePage uid={page.split("/")[1]} changePage={setPage} getUser={getCurrentUser} getToken={getCurrentSessionToken} />
    }
    {page.split("/")[0] == "comments" &&
      <CommentsPage mid={page.split("/")[1]} changePage={setPage} getUser={getCurrentUser} getToken={getCurrentSessionToken} uid={page.split("/")[2]}/>
    }
    </>
  )
  }

export default App;
