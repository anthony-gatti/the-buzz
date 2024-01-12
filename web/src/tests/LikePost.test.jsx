import { render, screen, fireEvent } from '@testing-library/react'
import '@testing-library/jest-dom';
import DisplayMessage from "../components/DisplayMessage";

test("Renders LikePost component", () => {
    render(<DisplayMessage title="test1" message="message1" id="id1" />);
    const likes = screen.getByTestId("like-post-component");
  
    // Assert that the LikePost component is rendered
    expect(likes).toBeInTheDocument();
  })
  
  test("Handles LikePost button click", () => {
    render(<DisplayMessage title="test1" message="message1" id="id1" />);
  
    const likeButton = screen.getByAltText('unupvoted');
      
    // Simulate a button click event
    fireEvent.click(likeButton);
  
    const upvoted = screen.getByAltText("upvoted");
    expect(upvoted).toBeInTheDocument();
  })
test("Handles LikePost downvote", () => {
    render(<DisplayMessage title="test1" message="message1" id="id1" />);
  
    const likeButton = screen.getByAltText('undownvoted');
      
    // Simulate a button click event
    fireEvent.click(likeButton);
  
    const downvoted = screen.getByAltText("downvoted");
    expect(downvoted).toBeInTheDocument();
  })

  test("Handles 2 LikePost downvotes", () => {
    render(<DisplayMessage title="test1" message="message1" id="id1" />);
  
    const likeButton = screen.getByAltText('undownvoted');
      
    // Simulate a button click event
    fireEvent.click(likeButton);
    const dislike = screen.getByAltText("downvoted");
    fireEvent.click(dislike);
    expect(likeButton).toBeInTheDocument();
  })

  test("Handles 2 LikePost upvotes", () => {
    render(<DisplayMessage title="test1" message="message1" id="id1" />);
  
    const likeButton = screen.getByAltText('unupvoted');
      
    // Simulate a button click event
    fireEvent.click(likeButton);
    const reupvote = screen.getByAltText("upvoted");
    fireEvent.click(reupvote);
    expect(likeButton).toBeInTheDocument();
  })

  test("Handles LikePost upvote then downvote", () => {
    render(<DisplayMessage title="test1" message="message1" id="id1" />);
  
    const likeButton = screen.getByAltText('unupvoted');
      
    // Simulate a button click event
    fireEvent.click(likeButton);
    const downvote = screen.getByAltText("undownvoted");
    fireEvent.click(downvote);
    const downvoted = screen.getByAltText("downvoted");
    expect(downvoted).toBeInTheDocument();
  })
  test("Handles LikePost downvote then upvote", () => {
    render(<DisplayMessage title="test1" message="message1" id="id1" />);
  
    const likeButton = screen.getByAltText('unupvoted');
    const downvote = screen.getByAltText("undownvoted");
    // Simulate a button click event
    fireEvent.click(downvote);
    
    fireEvent.click(likeButton);
    const upvoted = screen.getByAltText("upvoted");
    expect(upvoted).toBeInTheDocument();
  })