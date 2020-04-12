echo "[SETUP] VAGRANT SETUP SCRIPT FOR FRESH UBUNTU IMAGE"

echo "[SETUP] Make sure existing packages are up-to-date"
sudo apt-get --assume-yes update
sudo apt-get --assume-yes upgrade

echo "[SETUP] Install JDK (Java 13)"
sudo apt-get --assume-yes install openjdk-13-jdk

echo "[SETUP] Make sure we're in personal folder, then download Gradle 6.3"
cd ~
curl -LO 'https://services.gradle.org/distributions/gradle-6.3-bin.zip'

echo "[SETUP] Install unzip then unzip Gradle 6.3"
sudo apt-get --assume-yes install unzip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-6.3-bin.zip

echo "[SETUP] Add Gradle 6.3 to PATH"
sudo echo "source /vagrant/scripts/export.sh" >> /home/vagrant/.bashrc

echo "[SETUP] Do an initial compile of the project"
cd /vagrant
/opt/gradle/gradle-6.3/bin/gradle assemble

echo "[SETUP] Clone BDD-Security so it's available for end-to-end testing"
cd ~
git clone https://github.com/gingeleski/bdd-security.git

echo "[SETUP] Do an initial compile of BDD-Security"
cd bdd-security
/opt/gradle/gradle-6.3/bin/gradle assemble
