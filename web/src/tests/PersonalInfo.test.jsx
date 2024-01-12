import { render, screen, fireEvent } from '@testing-library/react'
import '@testing-library/jest-dom';
import PersonalInfo from '../components/PersonalInfo';

//Functions to mock functions passed as props to the component
const getInfo = () => {
    return {
        uEmail: "test@test.test",
        uUsername: "testing1",
        uId: "3"
    };
}
const getUser = () => {
    return "4"
}
const getUserSame = () => {
    return "3";
}

test("Personal Info loads correctly with different uid", () => {
    render(<PersonalInfo getInfo={getInfo} getUser={getUser} />)
    //Get various parts of personal info section
    const header = screen.getByText(/Personal Information/i);
    const userEdit = screen.getByText(/Username:/i);
    const email = screen.getByText(/test@test.test/i);
    const username = screen.getByText(/testing1/i);
    const editButton = screen.queryByText(/edit/i);
    //assert them all to be there except the edit button since they are different users
    expect(header).toBeInTheDocument();
    expect(userEdit).toBeInTheDocument();
    expect(email).toBeInTheDocument();
    expect(username).toBeInTheDocument();
    expect(editButton).toBeNull();
})
test("Personal Info loads correctly with same uid", () => {
    render(<PersonalInfo getInfo={getInfo} getUser={getUserSame} />)
    //get all parts that should be on the screen
    const header = screen.getByText(/Personal Information/i);
    const userEdit = screen.getByText(/Username:/i);
    const email = screen.getByText(/test@test.test/i);
    const username = screen.getByText(/testing1/i);
    const editButton = screen.getByTestId("username-edit");
    const editButton2 = screen.getByTestId("email-edit");
    //assert they are all there, including the edit buttons
    expect(header).toBeInTheDocument();
    expect(userEdit).toBeInTheDocument();
    expect(email).toBeInTheDocument();
    expect(username).toBeInTheDocument();
    expect(editButton).toBeInTheDocument();
    expect(editButton2).toBeInTheDocument();
})

test("Personal Info renders correctly after editing", () => {
    render(<PersonalInfo getInfo={getInfo} getUser={getUserSame} />)
    const editButton = screen.getByTestId("username-edit");
    const editButton2 = screen.getByTestId("email-edit");
    //edit username
    fireEvent.click(editButton);
    const saveButton = screen.getByTestId('username-save');
    const input = screen.getByTestId('username-input');
    //save changes
    fireEvent.change(input, {target: {value: 'testing2'}});
    fireEvent.click(saveButton);
    //edit email
    fireEvent.click(editButton2);
    const input2 = screen.getByTestId('email-input');
    const saveButton2 = screen.getByTestId('email-save');
    //save changes
    fireEvent.change(input2, {target: {value: 'a@a.a'}});
    fireEvent.click(saveButton2);

    const newusername = screen.getByText(/testing2/i);
    const newemail = screen.getByText(/a@a.a/i);
    //chack for new info and edit buttons
    expect(newemail).toBeInTheDocument();
    expect(newusername).toBeInTheDocument();
    const editButton3 = screen.getByTestId("username-edit");
    const editButton4 = screen.getByTestId("email-edit");
    expect(editButton3).toBeInTheDocument();
    expect(editButton4).toBeInTheDocument();
}) 