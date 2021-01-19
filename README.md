# Verifiable-Random-Function-in-Java
This repository has the basic files/functions for a verifiable random function.

This implementation is based on the version 8 of the Internet-Draft from IETF (https://tools.ietf.org/html/draft-irtf-cfrg-vrf-08) and only covers the RSA implementation.
It is composed of 5 files:
*DataConvertionPrimitives with the respective data conversion primitives needed;
*MGF1 only with a MGF1 implementation;
*Operations for byte operations;
*Rsa_signature_primitives with a implementation of RSASP1 and RSAVP1 described in (https://tools.ietf.org/html/rfc8017#section-5);
*Rsa_vrf with the basic methods for a VRF implementation.

# Disclaimer
The code implemented in this repository might contain errors and is not for commercial use;
