import { render, screen, fireEvent } from '@testing-library/react'
import '@testing-library/jest-dom';
import NewComment from '../components/NewComment';

test("Loads correctly", () => {
    render(<NewComment />)
    //get new comment inputs and buttons
    const header = screen.getByText(/comment:/i);
    const saveButton = screen.getByText(/Save/i);
    const input = screen.getByTestId(/new-comment-input/i);
    //assert that they are all on the screen
    expect(header).toBeInTheDocument();
    expect(saveButton).toBeInTheDocument();
    expect(input).toBeInTheDocument();
})