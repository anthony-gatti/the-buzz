import { render, screen, fireEvent } from '@testing-library/react'
import '@testing-library/jest-dom';
import Header from '../components/Header';

test("Renders Header correctly", () => {
    render(<Header />)
    // Get elements from screen
    const title = screen.getByText(/the buzz/i);
    const image = screen.queryByAltText(/profile/i);
    // Check that title is there and profile image is not
    expect(image).toBeNull();
    expect(title).toBeInTheDocument();
}) 