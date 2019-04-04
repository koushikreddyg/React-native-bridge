//
//  Counter.swift
//  iosbridge
//
//  Created by Koushik Reddy on 4/3/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation
@objc(Counter)
class Counter: NSObject {
  @objc
  func constantsToExport() -> [AnyHashable : Any]! {
    return ["initialCount": 0]
  }
}
