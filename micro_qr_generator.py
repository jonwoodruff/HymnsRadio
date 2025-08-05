import qrcode
from qrcode.constants import ERROR_CORRECT_L

def generate_micro_qr(text, output_file="micro_qr.png"):
    # Create a small QR Code (approximating a Micro QR)
    qr = qrcode.QRCode(
        version=1,  # Version 1 is 21x21 modules
        error_correction=ERROR_CORRECT_L,  # Lowest error correction
        box_size=10,
        border=2,
    )
    
    qr.add_data(text)
    qr.make(fit=True)

    # Create and save the image
    img = qr.make_image(fill_color="black", back_color="white")
    img.save(output_file)
    print(f"Micro QR Code saved as: {output_file}")

if __name__ == "__main__":
    # Example usage
    user_input = input("Enter text to encode as Micro QR Code: ")
    generate_micro_qr(user_input)
