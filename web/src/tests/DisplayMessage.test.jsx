import { render, screen, fireEvent } from '@testing-library/react'
import '@testing-library/jest-dom';
import DisplayMessage from "../components/DisplayMessage";

test("Display message renders correctly", () => {
  render(<DisplayMessage title="test1" message="message1" uid="id1" />);

  const title = screen.getByText(/test1/i);
  const message = screen.getByText(/message1/i);
  const id = screen.getByText(/id1/i);

  // Assert that the component renders the provided title, id, and message
  expect(title).toBeInTheDocument();
  expect(message).toBeInTheDocument();
  expect(id).toBeInTheDocument();
})

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