//
//  Project.h
//  MeetingBuilder
//
//  Created by Brodie Lockard on 2/2/14.
//  Copyright (c) 2014 CSSE371. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@interface Project : NSManagedObject

@property (nonatomic, retain) NSNumber * projectID;
@property (nonatomic, retain) NSString * projectTitle;
@property (nonatomic, retain) NSString * members;
@property (nonatomic, retain) NSString * tasks;
@property (nonatomic, retain) NSNumber * userID;

@end