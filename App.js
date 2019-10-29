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
    NativeModules.LocationSettings.sendData({array: [{
      name: "Koushik",
      age: 24,
      // job:{company: "AT&T", location: 'Plano'}
    },{
      name: "yunus",
      age: 27,
      job:{company: "AT&T", location: 'Pppp'}
    }]})


    // NativeModules.LocationSettings.arrayForEach().then((res)=>{
    //   console.warn(res.replace('=',':'))
      // JSON.parse(res).map((item)=>{
      //   console.warn(item.name)
      //   NativeModules.LocationSettings.sendIndObject(item);
      // })
    // })

    

    
    // TouchId.authenticate().then((res)=>{console.warn(res)}).catch(res=>console.warn(res))
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


  }
  render() {
    // Platform.OS === 'android' ? NativeModules.Counter.show(res => console.warn(res)) : console.warn(NativeModules.Counter)
    return (
      <View style={styles.container}>
        <Text>Hi</Text>
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