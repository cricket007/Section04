//
//  iWinEditProfileTest.m
//  MeetingBuilder
//
//  Created by CSSE Department on 12/13/13.
//  Copyright (c) 2013 CSSE371. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "iWinViewProfileViewController.h"
#import "iWinEditProfileViewController.h"

@interface iWinEditProfileTest : XCTestCase
@property (strong, nonatomic) iWinEditProfileViewController *editVC;
@property (strong, nonatomic) iWinViewProfileViewController *viewVC;
@end

@implementation iWinEditProfileTest

- (void)setUp
{
    [super setUp];
    // Put setup code here; it will be run once, before the first test case.
    self.editVC = [[iWinEditProfileViewController alloc] initWithNibName:@"iWinEditProfileViewController" bundle:nil];
    //self.viewVC = [[iWinViewProfileViewController alloc] initWithNibName:@"iWinViewProfileViewController" bundle:nil];
    
    //[self.editVC viewDidLoad];
}

- (void)tearDown
{
    // Put teardown code here; it will be run once, after the last test case.
    [super tearDown];
}

- (void)testCompanyChange
{
    
}

- (void)testEmailChange
{
    
}

- (void)testPhoneChange
{
    
}

- (void)testPositionChange
{
    
}

- (void)testMoreAboutMeChange
{
    
}


- (void)testChangedPicture
{
    
}

- (void)testExample
{
    //XCTFail(@"No implementation for \"%s\"", __PRETTY_FUNCTION__);
}

@end