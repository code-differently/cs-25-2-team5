import React from "react";

interface GuestInputProps {
  guestInput: string;
  setGuestInput: (v: string) => void;
  handleAddGuest: () => void;
  handleKeyPress: (e: React.KeyboardEvent<HTMLInputElement>) => void;
  isSubmitting: boolean;
}

const GuestInput: React.FC<GuestInputProps> = ({
  guestInput,
  setGuestInput,
  handleAddGuest,
  handleKeyPress,
  isSubmitting
}) => (
  <div className="form-group">
    <label>Add Guests</label>
    <input
      type="email"
      className="event-form-input add-guests-input"
      placeholder="guest@example.com"
      value={guestInput}
      onChange={e => setGuestInput(e.target.value)}
      onKeyDown={handleKeyPress}
      disabled={isSubmitting}
    />
    <button
      type="button"
      className="add-guests-button"
      onClick={handleAddGuest}
      disabled={isSubmitting}
    >
      Add Guest
    </button>
  </div>
);

export default GuestInput;
