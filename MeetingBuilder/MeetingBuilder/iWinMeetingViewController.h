//
//  iWinProjectViewController.h
//  MeetingBuilder
//
//  Created by CSSE Department on 10/2/13.
//  Copyright (c) 2013 CSSE371. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "iWinScheduleViewMeetingViewController.h"
#import "CustomSubtitledCell.h"

@protocol ReloadScheduleDelegate <NSObject>

-(void)loadScheduleView;

@end

@interface iWinMeetingViewController : UIViewController <UITableViewDataSource, UITableViewDelegate, UIAlertViewDelegate, NSURLConnectionDelegate, ViewMeetingDelegate, SubtitledCellDelegate>
@property (strong, nonatomic) NSMutableData *responseData;
@property (weak, nonatomic) IBOutlet UIButton *scheduleMeetingButton;
@property (weak, nonatomic) IBOutlet UITableView *projectTable;
@property (nonatomic) id<ReloadScheduleDelegate> reloadScheduleDelegate;
- (IBAction)onScheduleNewMeeting;
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil withID:(NSInteger)userID;
@end