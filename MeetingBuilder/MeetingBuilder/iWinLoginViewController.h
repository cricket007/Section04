//
//  iWinLoginViewController.h
//  MeetingBuilder
//
//  Created by CSSE Department on 10/2/13.
//  Copyright (c) 2013 CSSE371. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol iWinLoginDelegate <NSObject>

-(void)login:(NSInteger)userID;
-(void)joinUs;

@end

@interface iWinLoginViewController : UIViewController <NSURLConnectionDelegate, UITextFieldDelegate>
@property (strong, nonatomic) NSMutableData *responseData;
@property (weak, nonatomic) IBOutlet UITextField *userNameField;
@property (weak, nonatomic) IBOutlet UITextField *passwordField;
@property (weak, nonatomic) IBOutlet UIButton *loginButton;
@property (weak, nonatomic) id<iWinLoginDelegate>loginDelegate;
@property (weak, nonatomic) IBOutlet UISwitch *rememberSwitch;
- (IBAction)onClickLogin:(id)sender;
- (IBAction)onClickJoinUs:(id)sender;

@end