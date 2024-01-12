// https://www.youtube.com/watch?v=JBSUgDxICg8
// https://www.browserstack.com/guide/unit-testing-of-react-apps-using-jest

/**
 * @jest-environment */

import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import NewPost from '../components/NewPost'; 

describe(NewPost, () => {
  it('submits valid input, performs post request, and clears the form', async () => {
    const fakeResponse = { mStatus: 'ok' };
    global.fetch = jest.fn().mockResolvedValue({
      ok: true,
      json: () => Promise.resolve(fakeResponse),
    });

    const { getByPlaceholderText, getByText } = render(<NewPost />);

    // Fill the form with valid input
    fireEvent.change(getByPlaceholderText('Title'), { target: { value: 'Test Title' } });
    fireEvent.change(getByPlaceholderText('Message'), { target: { value: 'Test Message' } });

    // Click the 'CREATE POST' button
    fireEvent.click(getByText('CREATE POST'));

    // wait for the fetch to complete and the form to clear
    await waitFor(() => {
      // check if the form is cleared after successful submission
      expect(getByPlaceholderText('Title').value).toBe('');
      expect(getByPlaceholderText('Message').value).toBe('');
    });
  });

  it('displays alert for invalid input and does not perform post request', async () => {
    global.window.alert = jest.fn();

    const { getByPlaceholderText, getByText } = render(<NewPost />);

    // Fill the form with invalid input (empty fields)
    fireEvent.change(getByPlaceholderText('Title'), { target: { value: '' } });
    fireEvent.change(getByPlaceholderText('Message'), { target: { value: '' } });

    // Click the 'CREATE POST' button
    fireEvent.click(getByText('CREATE POST'));

    // Check if an alert is displayed for invalid input
    expect(global.window.alert).toHaveBeenCalledWith(
      'Input not valid. Must include a title and message, with the message under 2050 characters'
    );

    // Check if fetch function was not called for invalid input
    expect(global.fetch).not.toHaveBeenCalled();
  });

  it('handles server errors and displays an error alert', async () => {
    global.fetch = jest.fn().mockResolvedValue({
      ok: false,
      status: 500,
      statusText: 'Internal Server Error',
    });

    global.window.alert = jest.fn();

    const { getByPlaceholderText, getByText } = render(<NewPost />);

    // Fill the form with valid input
    fireEvent.change(getByPlaceholderText('Title'), { target: { value: 'Test Title' } });
    fireEvent.change(getByPlaceholderText('Message'), { target: { value: 'Test Message' } });

    // Click the 'CREATE POST' button
    fireEvent.click(getByText('CREATE POST'));

    // Wait for the alert to appear
    await waitFor(() => {
      // Check if an alert is displayed for server error
      expect(global.window.alert).toHaveBeenCalledWith('Unspecified error');
    });
  });

  it('handles network errors and displays an error alert', async () => {
    global.fetch = jest.fn().mockRejectedValue(new Error('Network error'));
    global.window.alert = jest.fn();

    const { getByPlaceholderText, getByText } = render(<NewPost />);

    // Fill the form with valid input
    fireEvent.change(getByPlaceholderText('Title'), { target: { value: 'Test Title' } });
    fireEvent.change(getByPlaceholderText('Message'), { target: { value: 'Test Message' } });

    // Click the 'CREATE POST' button
    fireEvent.click(getByText('CREATE POST'));

    // Wait for the alert to appear
    await waitFor(() => {
      // Check if an alert is displayed for network error
      expect(global.window.alert).toHaveBeenCalledWith('Unspecified error');
    });
  });
});