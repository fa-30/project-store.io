import React, { useEffect, useState } from "react";
import CryptoJS from "crypto-js";
import Cards from "react-credit-cards-2";
import { Modal, Button } from "react-bootstrap";
import "react-credit-cards-2/dist/es/styles-compiled.css";
import toast from "react-hot-toast";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const MyModal = ({ show, hide, onClick: confirmOnlinePayment }) => {
  const [cardInfo, setCardInfo] = useState({
    number: "",
    name: "",
    expiry: "",
    cvv: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCardInfo({ ...cardInfo, [name]: value });
  };

  return (
    <Modal dialogClassName="w-75" show={show} onHide={hide}>
      <Modal.Header closeButton>
        <Modal.Title>Credit Card</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Cards
          number={cardInfo.number}
          name={cardInfo.name}
          expiry={cardInfo.expiry}
          cvv={cardInfo.cvv}
          acceptedCards={"visa mastercard american-express"}
        />
        <div className="container mt-4">
          <div className="row mb-4 justify-content-center">
            <div className="col-md-10">
              <label htmlFor="cardNumber" className="form-label">
                Card Number
              </label>
              <input
                type="text"
                maxLength={16}
                className="form-control"
                id="cardNumber"
                placeholder="---- ---- ---- ----"
                name="number"
                onChange={handleInputChange}
                value={cardInfo.number}
              />
            </div>
          </div>
          <div className="row mb-4 justify-content-center">
            <div className="col-md-10">
              <label htmlFor="OwnerName" className="form-label">
                Name
              </label>
              <input
                type="text"
                className="form-control"
                id="OwnerName"
                placeholder="John Doe"
                name="name"
                onChange={handleInputChange}
                value={cardInfo.name}
              />
            </div>
          </div>
          <div className="row justify-content-center">
            <div className="col-md-10">
              <label htmlFor="isValid" className="form-label">
                Expiry
              </label>
              <input
                type="text"
                className="form-control"
                autoComplete="cc-exp"
                inputMode="numeric"
                id="isValid"
                placeholder="MM/YY"
                name="expiry"
                onChange={handleInputChange}
                value={cardInfo.expiry}
              ></input>
            </div>
          </div>
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={hide}>
          Close
        </Button>
        <Button variant="primary" onClick={confirmOnlinePayment}>
          Confirm Payment
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default MyModal;
