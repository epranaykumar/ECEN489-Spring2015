## Introduction
Electrical signals are carried between points in one of two ways: Via transmission line or through empty space using antennas.  
  
**A transmission line** confines the signal or energy to the region inside or nearby the transmission line in order to transfer information. Coaxial cable and HDMI cable are examples of a transmission line.  
  
**An antenna** encourages electrical signals to reach far distances from the antenna. By definition, An antenna is a reciprocal and passive device that radiates and receives the RF or microwave signal. Being a reciprocal device meaning an antenna's power pattern for receiving and transmitting are identical. An antenna can not increase the amount of energy that is delivered to it because it is a passive device.  

Examples of antennas are:  
1. Wire antennas: Dipole, Monopole, helix  
2. Aperture antennas: horn, slot  
3. Printed antennas: patch, spiral  

![antennas](https://scontent-dfw.xx.fbcdn.net/hphotos-xfa1/v/t1.0-9/11043067_10152805153589426_172160379886024506_n.jpg?oh=8125cf0816b1dc31b3877b089b89ae55&oe=558D962B)

## Three types of antennas
According to antennas' ability to direct their power, antennas can be classified into three types: isotropic antenna, omnidirectional antenna and directional antenna.  

**Isotropic Antenna**  
An isotropic antenna is a hypothetical antenna radiating the same intensity of radio waves in all directions. The best way to visualize it is to think how the sound wave propagates when a firecracker goes off in the air.  
It does not exist in practical; it is important to know what it is because an antenna's directivity is relative to isotropic antenna's directivity. Isotropic antenna has directivity of 1 or 0dBi. All other antennas have directivity greater than 1 or 0 dBi.  
![Isotropic antenna](http://www.dx-antennas.com/Radiation_plot/Isotropic%201.png)

**Omnidirectional Antenna**  
An omnidirectional antenna radiates EM wave (signal) uniformly in all directions in one plane. Unlike isotropic antennas, dipole antennas are real antennas. The dipole radiation pattern is 360 degrees in the horizontal plane and approximately 75 degrees in the vertical plane (this assumes the dipole antenna is standing vertically) and resembles a donut in shape. Examples are monopole and dipole.  
![dipole](http://www.cisco.com/c/dam/en/us/products/collateral/wireless/aironet-antennas-accessories/prod_white_paper0900aecd806a1a3e.doc/_jcr_content/renditions/0900aecd806a1a3e_null_null_null_08_07_07-04.jpg)

**Directional Antenna**  
A directional antenna radiates greater power in one or more directions. Examples are horn antenna, patch antenna, Cantenna, Yagi-Uda antenna.  
![Patch Antenna](http://www.cisco.com/c/dam/en/us/products/collateral/wireless/aironet-antennas-accessories/prod_white_paper0900aecd806a1a3e.doc/_jcr_content/renditions/0900aecd806a1a3e_null_null_null_08_07_07-07.jpg)

## For us
For the purpose of the second project, a directional antenna is the best candidate to complete the task, and the antenna parameters that come into play are all embedded in Friss transmission equation.  
![Friis Transimission](http://upload.wikimedia.org/math/3/7/c/37c90d4012579624d932fa9aa6bb07b1.png)  
Gain = Directivity * efficiency  
Efficiency = Power Radiated / (Power Radiated + Power dissipated in ohmic losses on the antenna)


## Sources
[Antenna Patterns and Their Meaning](http://www.cisco.com/c/en/us/products/collateral/wireless/aironet-antennas-accessories/prod_white_paper0900aecd806a1a3e.html  )  
[Omni Antenna vs. Directional Antenna](http://www.cisco.com/c/en/us/support/docs/wireless-mobility/wireless-lan-wlan/82068-omni-vs-direct.html)  
[Friis transmission equation](http://en.wikipedia.org/wiki/Friis_transmission_equation)   
[Free-space path loss](http://en.wikipedia.org/wiki/Free-space_path_loss)
