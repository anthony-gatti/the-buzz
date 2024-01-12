import { render, screen, fireEvent } from '@testing-library/react'
import '@testing-library/jest-dom';
import Comment from '../components/Comment';

/**
 * Sample method to be passed as props and return data in this component
 */
const getUser = () => {
    return "3";
}

test("Comment renders correctly with same user", () => {
    render(<Comment  message="testMessage" time="Just Now" uid="3" getUser={getUser}/>);
    // get various elements of comment on website
    const message = screen.getByText(/testMessage/i);
    const editbutton = screen.getByTestId('comment-edit-button');
    const user = screen.getByText(/3/);
    const postTime = screen.getByText(/Just Now/i);

    expect(message).toBeInTheDocument();
    expect(editbutton).toBeInTheDocument();
    expect(user).toBeInTheDocument();
    expect(postTime).toBeInTheDocument();
})

test("Comment renders correctly with different user", () => {
    render(<Comment time="Just Now" message="testMessage" uid="4" getUser={getUser}/>);
    // get various elements of comment on website
    const message = screen.getByText(/testMessage/i);
    const editbutton = screen.queryByTestId('comment-edit-button');
    const user = screen.getByText(/4/);
    const postTime = screen.getByText(/Just Now/i);

    //Expect all pieces of comment to be on the screen, except editing since different users
    expect(message).toBeInTheDocument();
    expect(editbutton).toBeNull();
    expect(user).toBeInTheDocument();
    expect(postTime).toBeInTheDocument();
})

test("Comment renders correctly after editing", () => {
    render(<Comment time="Just Now" message="testMessage" id="3" getUser={getUser}/>)
    const editbutton = screen.queryByTestId('comment-edit-button');
    //Click edit button
    fireEvent.click(editbutton);
    const saveButton = screen.getByTestId('comment-save-button');
    const input = screen.getByTestId('comment-edit-input');
    //Add new value into input
    fireEvent.change(input, {target: {value: 'commentTest'}});
    //Click save button
    fireEvent.click(saveButton);

    const newNote = screen.getByText(/commentTest/i);
    //Check for new value and edit button
    expect(newNote).toBeInTheDocument();
    const editButton = screen.getByTestId('comment-edit-button');
    expect(editButton).toBeInTheDocument();
})