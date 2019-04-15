import React, { Component } from 'react';
import {
  StyleSheet,
  Text, 
  View, 
  NativeModules, 
  TouchableOpacity, 
  AppState, 
  DeviceEventEmitter, 
  PermissionsAndroid
} from 'react-native';
import TouchId from 'react-native-touch-id';


// const { LocationSettings } = NativeModules;
export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = { appState: AppState };
  }
  componentDidMount() {
    TouchId.authenticate().then((res)=>{console.warn(res)}).catch(res=>console.warn(res))
    // LocationSettings._startListen();
    // AppState.addEventListener('change', this._handleAppStateChange);
    // // this.checkLocationSettings();

    // DeviceEventEmitter.addListener('locationProviderStatusChange', (locationStatus) => {
    //   console.warn("gps listener has chhanged", locationStatus)
    // })
  }

  openLocationSettings = () => {
    // LocationSettings.openLocationSettings();
  }
  openGPSSettings = () => {
    // LocationSettings.openGPSSettings();
  }
  componentWillUnmount() {
    // AppState.removeEventListener('change', this._handleAppStateChange);
  }
  _handleAppStateChange = (nextAppState) => {
    // if (nextAppState === 'active') {
    //   this.setState({ appState: nextAppState });
    // }
    // this.checkLocationSettings();

  };

  checkLocationSettings = () => {
    // PermissionsAndroid.request(PermissionsAndroid.PERMISSIONS.CAMERA).then(res=>console.warn(res))
    // LocationSettings.isLocationEnabled((gps) => {
    //   PermissionsAndroid
    //     .check(PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION)
    //     .then(location => {
    //       if (!location && !gps) {
    //         this.openGPSSettings();
    //         this.openLocationSettings();
    //       } else if (!location && gps) {
    //         this.openLocationSettings();
    //       } else if (location && !gps) {
    //         this.openGPSSettings();
    //       }

    //     })
    // })

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