import { render, screen, fireEvent } from '@testing-library/react'
import '@testing-library/jest-dom';
import Bio from '../components/Bio';

/**
 * Sample methods to be passed as props and return data in this component
 */
const getUser = () => {
    return "3";
}
const getInfo = () => {
    return {
        uNote: "latenight"
    };
}

test("Bio renders correctly with same user", () => {
    render(<Bio id="3" getInfo={getInfo} getUser={getUser}/>)
    // get various elements of bio on website
    const header = screen.getByText(/Bio/i);
    const note = screen.getByText(/Note:/i);
    const button = screen.getByTestId('note-edit-button');
    
    const noteValue = screen.getByText(/latenight/i);

    //Expect all pieces of bio to be on the screen, including editting since same users
    expect(noteValue).toBeInTheDocument();

    expect(header).toBeInTheDocument();
    expect(note).toBeInTheDocument();
    expect(button).toBeInTheDocument();
})

test("Bio renders correctly with different user", () => {
    // get various elements of bio on website
    render(<Bio id="4" getInfo={getInfo} getUser={getUser}/>)
    const header = screen.getByText(/Bio/i);
    const note = screen.getByText(/Note:/i);
    const button = screen.queryByTestId('note-edit-button');
    
    const noteValue = screen.getByText(/latenight/i);
    //Expect all pieces of bio to be on the screen, but not editting since different users
    expect(noteValue).toBeInTheDocument();

    expect(header).toBeInTheDocument();
    expect(note).toBeInTheDocument();
    expect(button).toBeNull();
})

test("Bio renders correctly after editing", () => {
    render(<Bio id="3" getInfo={getInfo} getUser={getUser}/>)
    const button = screen.getByTestId('note-edit-button');
    //click edit
    fireEvent.click(button);
    const saveButton = screen.getByTestId('note-save-button');
    const input = screen.getByTestId('note-input');
    // Put new value in the input
    fireEvent.change(input, {target: {value: 'noteTest'}});
    //Click save
    fireEvent.click(saveButton);

    const newNote = screen.getByText(/noteTest/i);
    //Check for new value on the page
    expect(newNote).toBeInTheDocument();
    const editButton = screen.getByTestId('note-edit-button');
    expect(editButton).toBeInTheDocument();
}) 