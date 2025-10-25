import React from "react";

interface GuestListProps {
  guests: string[];
  handleRemoveGuest: (index: number) => void;
  isSubmitting: boolean;
}

const GuestList: React.FC<GuestListProps> = ({ guests, handleRemoveGuest, isSubmitting }) => (
  guests.length > 0 ? (
    <div className="guests-list">
      <h3>Guest List ({guests.length}):</h3>
      <ul>
        {guests.map((guest, index) => (
          <li key={index}>
            <span>{guest}</span>
            <button
              type="button"
              onClick={() => handleRemoveGuest(index)}
              className="remove-guest-button"
              disabled={isSubmitting}
            >
              Remove
            </button>
          </li>
        ))}
      </ul>
    </div>
  ) : null
);

export default GuestList;
