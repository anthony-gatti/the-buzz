import { render, screen, fireEvent, waitFor } from '@testing-library/react'
import '@testing-library/jest-dom';
import DisplayPost from '../components/DisplayPost';

/*test("Renders test message", () => {
    render(<DisplayPost />)
    const title = screen.getByText(/Testing, testing/i);
    const message = screen.getByText(/dkafhfkjahfj hif hjafhjdh af a/i);
    const id = screen.getByText(/Matthew Bergin/i);

    // Assert that the component renders the provided title, id, and message
    expect(title).toBeInTheDocument();
    expect(message).toBeInTheDocument();
    expect(id).toBeInTheDocument();
})*/

test("Renders data from server", async () => {
    // use a fake set of data to display
    const fakeData = {
        mData: [
          { mSubject: 'Test Subject 1', mMessage: 'Test Message 1' },
          { mSubject: 'Test Subject 2', mMessage: 'Test Message 2' },
        ],
      };
    // mock a valid get request to the server with reponse okay and return the data
    global.fetch = jest.fn().mockResolvedValue({
        ok: true,
        json: () => Promise.resolve(fakeData),
      });
    render(<DisplayPost />);
    await waitFor(() => {
        // check if the rendered messages are shown
        expect(screen.getByText(/Test Subject 1/i)).toBeInTheDocument();
        expect(screen.getByText(/Test Message 1/i)).toBeInTheDocument();
        expect(screen.getByText(/Test Subject 2/i)).toBeInTheDocument();
        expect(screen.getByText(/Test Message 2/i)).toBeInTheDocument();
    });
})

test("Handles server error and displays alert", async () => {
    // mock the get request and make it return a server error
    global.fetch = jest.fn().mockResolvedValue({
        ok: false,
        status: 500,
        statusText: 'Internal Server Error',
      });
  
      // mock the window alert for the error
      global.window.alert = jest.fn();
  
      render(<DisplayPost />);
  
      // check if the error alert is displayed and it should say "unspecified error"
      await waitFor(() => {
        expect(global.window.alert).toHaveBeenCalledWith('Unspecified error');
      })
})

test("handles network errors and displays alert", async () => {
    global.fetch = jest.fn().mockRejectedValue(new Error('Network error'));

    global.window.alert = jest.fn();

    render(<DisplayPost />)

    // check if the network error alert is displayed and it should say "unspecified error"
    await waitFor(() => {
        expect(global.window.alert).toHaveBeenCalledWith('Unspecified error');
    })
})