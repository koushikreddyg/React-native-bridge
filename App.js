import React, { Component } from 'react';
import { StyleSheet, Text, View, NativeModules, Platform, TouchableOpacity, AppState, DeviceEventEmitter, PermissionsAndroid } from 'react-native';



export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = { appState: AppState };
  }
  componentDidMount() {
    NativeModules.Counter._startListen();
    AppState.addEventListener('change', this._handleAppStateChange);
    // this.checkLocationSettings();

    DeviceEventEmitter.addListener('locationProviderStatusChange', (locationStatus) => {
      console.warn("gps listener has chhanged", locationStatus)
  })
  }

  openLocationSettings = () => {
    NativeModules.Counter.openLocationSettings();
  }
  openGPSSettings = () => {
    NativeModules.Counter.openGPSSettings();
  }
  componentWillUnmount() {
    AppState.removeEventListener('change', this._handleAppStateChange);
  }
  _handleAppStateChange = (nextAppState) => {
    if ( nextAppState === 'active') {
      this.setState({ appState: nextAppState });
    } 
    // this.checkLocationSettings();

  };

  checkLocationSettings = () => {
    NativeModules.Counter.isLocationEnabled((gps)=>{
      PermissionsAndroid
      .check(PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION)
      .then(location=>{
        if(!location && !gps){
          this.openGPSSettings();
          this.openLocationSettings();
        }else if(!location && gps){
          this.openLocationSettings();
        }else if(location && !gps){
          this.openGPSSettings();
        }
  
      })
    })
   
  }
  render() {
    // Platform.OS === 'android' ? NativeModules.Counter.show(res => console.warn(res)) : console.warn(NativeModules.Counter)
    return (
      <View style={styles.container}>
        <View style={styles.top} >
          <TouchableOpacity>
            <Text onPress={this.openLocationSettings}>open location settings</Text>
          </TouchableOpacity>
          <TouchableOpacity>
            <Text onPress={this.openGPSSettings}>open GPS settings</Text>
          </TouchableOpacity>
          <TouchableOpacity>
            <Text onPress={this.checkLocationSettings}>check for the location</Text>
          </TouchableOpacity>
        </View>
        {/* <Bulb style={ styles.bottom }  isOn={this.state.isOn} onStatusChange={this._onStatusChange} /> */}
      </View>
    );
  }
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5FCFF',
  },
  top: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  bottom: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
});