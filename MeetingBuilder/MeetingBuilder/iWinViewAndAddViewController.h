//
//  iWinViewAndAddViewController.h
//  MeetingBuilder
//
//  Created by CSSE Department on 10/24/13.
//  Copyright (c) 2013 CSSE371. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "iWinAgendaItemViewController.h"

@protocol AgendaDelegate <NSObject>
- (void) onSaveAgenda: (NSInteger) agendaID;
@end


@interface iWinViewAndAddViewController : UIViewController <UITableViewDataSource, UITableViewDelegate, AgendaItemDelegate, NSURLConnectionDelegate>
- (IBAction)onClickSave;
- (IBAction)onClickCancel;
- (IBAction)onClickAddItem;
- (IBAction)onClickAddAttendees;
@property (weak, nonatomic) IBOutlet UIButton *saveButton;
@property (weak, nonatomic) IBOutlet UIButton *cancelButton;
@property (weak, nonatomic) IBOutlet UILabel *headerLabel;
@property (weak, nonatomic) IBOutlet UITextField *titleTextField;
@property (weak, nonatomic) IBOutlet UIButton *addAttendeesButton;
@property (weak, nonatomic) IBOutlet UIButton *addItemButton;
@property (weak, nonatomic) IBOutlet UITableView *itemTableView;
@property (nonatomic) id<AgendaDelegate> agendaDelegate;
@property (nonatomic) BOOL isAgendaCreated;

@property (nonatomic, assign) NSInteger meetingID;
@property (nonatomic, assign) NSInteger userID;
@property (nonatomic) NSInteger agendaID;



- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil inEditMode:(BOOL)isEditing;
@end
