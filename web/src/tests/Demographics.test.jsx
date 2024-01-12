import { render, screen, fireEvent, userEvent } from '@testing-library/react'
import '@testing-library/jest-dom';
import Demographics from '../components/Demographics';

/**
 * Sample method to be passed as props and return data in this component
 */
const getInfo = () => {
    return {
        uSO: "None1",
        uGender: "None2"
    };
}

test("Demographics renders correctly", () => {
    render(<Demographics getInfo={getInfo} />);
    // get various elements of demographics on website, only shown if user is same so dont have to check
    const header = screen.getByText(/Demographics/i);
    const so = screen.getByText(/None1/i);
    const editgibutton = screen.getByTestId('gi-edit-button');
    const editsobutton = screen.getByTestId('so-edit-button');
    const gi = screen.getByText(/None2/);
    const giHeader = screen.getByText(/Gender Identity:/i);
    const soHeader = screen.getByText(/Sexual Orientation:/i);

    //Expect all pieces of demographics to be on the screen, including editting
    expect(header).toBeInTheDocument();
    expect(so).toBeInTheDocument();
    expect(editgibutton).toBeInTheDocument();
    expect(editsobutton).toBeInTheDocument();
    expect(gi).toBeInTheDocument();
    expect(giHeader).toBeInTheDocument();
    expect(soHeader).toBeInTheDocument();
})

test("Demographics can edit gi", () => {
    render(<Demographics getInfo={getInfo} />);
    const editgibutton = screen.getByTestId('gi-edit-button');
    //click edit button
    fireEvent.click(editgibutton);

    const saveButton = screen.getByTestId('gi-save-button');
    const input = screen.getByTestId('giField');
    //click save button
    fireEvent.click(saveButton);
    //check for new value
    const gi = screen.getByText(/Male/i);
    expect(gi).toBeInTheDocument();
})

test("Demographics can edit so", () => {
    render(<Demographics getInfo={getInfo} />);
    const editgibutton = screen.getByTestId('so-edit-button');
    //click edit button
    fireEvent.click(editgibutton);

    const saveButton = screen.getByTestId('so-save-button');
    const input = screen.getByTestId('soField');
    //click save button
    fireEvent.click(saveButton);
    //check for new value
    const gi = screen.getByText(/straight or heterosexual/i);
    expect(gi).toBeInTheDocument();
})